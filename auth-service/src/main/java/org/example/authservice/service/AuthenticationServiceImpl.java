package org.example.authservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.auth.AuthenticationRequestDTO;
import org.example.authservice.dto.auth.RefreshTokenRequestDTO;
import org.example.authservice.dto.auth.ValidateTokenRequest;
import org.example.authservice.dto.user.AddUserRequestDTO;
import org.example.authservice.dto.user.FullUserResponseDTO;
import org.example.authservice.mapper.UserMapper;
import org.example.authservice.repository.TokenRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.shared.constant.ErrorMessage;
import org.example.authservice.shared.constant.SuccessMessage;
import org.example.authservice.shared.entity.Token;
import org.example.authservice.shared.entity.User;
import org.example.authservice.shared.entity.enums.Role;
import org.example.authservice.shared.exception.*;
import org.example.authservice.shared.global.AuthData;
import org.example.authservice.shared.global.GlobalResponseDTO;
import org.example.authservice.shared.global.NoPaginatedMeta;
import org.example.authservice.shared.global.Status;
import org.example.authservice.shared.util.JwtTokenUtil;
import org.example.authservice.shared.util.MessageSourceUtil;
import org.example.authservice.shared.util.NetworkUtil;
import org.example.authservice.shared.validator.RequestValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository        userRepository;
    private final TokenRepository       tokenRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil          jwtUtil;
    private final MessageSourceUtil     messageSourceUtil;
    private final UserDetailsService userDetailsService;
    private static final String AUTH_PREFIX = "Bearer ";

    private Token generateRefreshToken(HttpServletRequest request, User owner) {
        return tokenRepository.save(
                Token.builder()
                     .ipAddress(NetworkUtil.getClientIpAddress(request))
                     .userAgent(NetworkUtil.getUserAgent(request))
                     .whenCreated(new Date(Instant.now().toEpochMilli()))
                     .whenExpired(new Date(
                             Instant.now()
                                    .plus(7, ChronoUnit.DAYS)
                                    .toEpochMilli()
                     ))
                     .owner(owner)
                     .build()
        );
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> validate(ValidateTokenRequest request) {
        String jwt = request.getToken().substring(AUTH_PREFIX.length());
        String username = jwtUtil.extractUsername(jwt);

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            userDetails = null;
        }

        return GlobalResponseDTO
                .<NoPaginatedMeta, FullUserResponseDTO>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Auth.AUTHENTICATED))
                        .build()
                )
                .data(userDetails == null
                      ? null
                      : FullUserResponseDTO.builder().username(userDetails.getUsername()).build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, AuthData> register(AddUserRequestDTO request, HttpServletRequest servletRequest) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_USERNAME);

        if (!RequestValidator.isBlankRequestParams(request.password()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_PASSWORD);

        if (!request.password().equals(request.confirmPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessage.User.MISMATCHED_PASSWORD);

        if (userRepository.findByUsername(request.username()).isPresent())
            throw new DuplicateUsernameException(ErrorMessage.Request.DUPLICATE_USERNAME);

        User user = User.builder()
                        .username(request.username())
                        .password(passwordEncoder.encode(request.password()))
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .email(request.email())
//                        .phoneNumber(request.phoneNumber())
                        .role(Role.CUSTOMER)
                        .build();
        User savedUser = userRepository.save(user);
        String jwtToken = jwtUtil.generateToken(user);

        return GlobalResponseDTO
                .<NoPaginatedMeta, AuthData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Auth.REGISTERED))
                        .build()
                )
                .data(AuthData.builder()
                              .accessToken(jwtToken)
                              .refreshToken(generateRefreshToken(servletRequest, savedUser).getId())
                              .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(savedUser))
                              .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, AuthData> authenticate(
            AuthenticationRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        User user = userRepository.findActiveUserByUsername(request.username())
                                  .orElseThrow(() -> new NotFoundException(ErrorMessage.User.NOT_FOUND));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String jwtToken = jwtUtil.generateToken(user);
        user.setLastLoggedIn(new Date(Instant.now().toEpochMilli()));
        user.setLoggedIn(user.getLoggedIn() == null ? 1 : user.getLoggedIn() + 1);
        User updatedUser = userRepository.save(user);

        LoginStatisticServiceImpl.modified = true;
        ++LoginStatisticServiceImpl.counter;

        return GlobalResponseDTO
                .<NoPaginatedMeta, AuthData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Auth.AUTHENTICATED))
                        .build()
                )
                .data(AuthData.builder()
                              .accessToken(jwtToken)
                              .refreshToken(generateRefreshToken(servletRequest, updatedUser).getId())
                              .loggedInUser(UserMapper.INSTANCE.toUserFullResponseDTO(updatedUser))
                              .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, AuthData> refresh(RefreshTokenRequestDTO request, HttpServletRequest servletRequest) {
        Optional<Token> found = tokenRepository.findById(request.refreshToken());
        if (found.isEmpty())
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        Token token = found.get();

        if (token.getWhenExpired().before(new Date()))
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        if (!token.getUserAgent().equals(NetworkUtil.getUserAgent(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_USER_AGENT);

        if (!token.getIpAddress().equals(NetworkUtil.getClientIpAddress(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_IP_ADDRESS);

        token.setWhenExpired(new Date(Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli()));
        tokenRepository.save(token);
        User user = token.getOwner();
        String jwtToken = jwtUtil.generateToken(user);
        return GlobalResponseDTO
                .<NoPaginatedMeta, AuthData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .build()
                )
                .data(AuthData.builder()
                              .accessToken(jwtToken)
                              .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> logout(RefreshTokenRequestDTO request, HttpServletRequest servletRequest) {
        Optional<Token> found = tokenRepository.findById(request.refreshToken());
        if (found.isEmpty())
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        Token token = found.get();

        if (token.getWhenExpired().before(new Date()))
            throw new AppException(ErrorMessage.Auth.SESSION_EXPIRED);

        if (!token.getUserAgent().equals(NetworkUtil.getUserAgent(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_USER_AGENT);

        if (!token.getIpAddress().equals(NetworkUtil.getClientIpAddress(servletRequest)))
            throw new AppException(ErrorMessage.Auth.ABNORMAL_IP_ADDRESS);

        tokenRepository.deleteById(request.refreshToken());

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Auth.LOGGED_OUT))
                        .build()
                )
                .build();
    }


}
