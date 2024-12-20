package org.example.userservice.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.auth.ValidateTokenRequest;
import org.example.userservice.dto.user.FullUserResponseDTO;
import org.example.userservice.feign.AuthServiceClient;
import org.example.userservice.shared.global.GlobalResponseDTO;
import org.example.userservice.shared.global.NoPaginatedMeta;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service(value = "appAuthorizer")
@RequiredArgsConstructor
public class AppAuthorizerImpl implements AppAuthorizer {
    private final AuthServiceClient authServiceClient;


    @Override
    public boolean authorize(Authentication authentication, Object callerObj, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> responseBody = authServiceClient.validate(
                ValidateTokenRequest.builder().token(token).build()
        ).getBody();

        if (responseBody == null) return false;

        FullUserResponseDTO user = responseBody.data();

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return user.username();
            }
        };

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return true;
    }
}
