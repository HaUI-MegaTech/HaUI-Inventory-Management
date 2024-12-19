package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.user.*;
import org.example.userservice.feign.FileServiceClient;
import org.example.userservice.kafka.KafkaProducer;
import org.example.userservice.kafka.ResetPasswordEvent;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.shared.constant.ErrorMessage;
import org.example.userservice.shared.constant.PaginationConstant;
import org.example.userservice.shared.constant.SuccessMessage;
import org.example.userservice.dto.common.ImportDataRequestDTO;
import org.example.userservice.shared.entity.User;
import org.example.userservice.shared.entity.enums.Role;
import org.example.userservice.shared.exception.*;
import org.example.userservice.shared.global.*;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.shared.util.MessageSourceUtil;
import org.example.userservice.shared.util.RandomUtil;
import org.example.userservice.shared.validator.RequestValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository    userRepository;
    private final MessageSourceUtil messageSourceUtil;
    private final PasswordEncoder   passwordEncoder;
    private final KafkaProducer     kafkaProducer;
    private final FileServiceClient fileServiceClient;

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> getOneUser(
            Integer userId
    ) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        return GlobalResponseDTO
                .<NoPaginatedMeta, FullUserResponseDTO>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.FOUND))
                        .build())
                .data(UserMapper.INSTANCE.toUserFullResponseDTO(foundUser.get()))
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BriefUserResponseDTO> addOneUser(
            @RequestBody AddUserRequestDTO request
    ) {
        if (!RequestValidator.isBlankRequestParams(request.username()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_USERNAME);

        if (!RequestValidator.isBlankRequestParams(request.password()))
            throw new AbsentRequiredFieldException(ErrorMessage.Request.BLANK_PASSWORD);

        if (!request.password().equals(request.confirmPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessage.User.MISMATCHED_PASSWORD);

        if (userRepository.findUserByUsername(request.username()).isPresent())
            throw new DuplicateUsernameException(ErrorMessage.Request.DUPLICATE_USERNAME);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BriefUserResponseDTO>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.ADDED_ONE))
                        .build()
                )
                .data(UserMapper.INSTANCE.toBriefUserResponseDTO(
                        userRepository.save(
                                User.builder()
                                    .username(request.username())
                                    .password(passwordEncoder.encode(request.password()))
                                    .firstName(request.firstName())
                                    .lastName(request.lastName())
                                    .email(request.email())
                                    .phoneNumber(request.phoneNumber())
                                    .role(Role.CUSTOMER)
                                    .build()))
                )
                .build();

    }

//    @Override
//    public GlobalResponseDTO<NoPaginatedMeta, Void> importExcelUser(
//            ImportDataRequestDTO request
//    ) {
//        if (ExcelUtil.notHasExcelFormat(request.file()))
//            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);
//
//        try {
//            List<User> users = ExcelUtil.excelToUsers(request.file().getInputStream());
//            List<User> savedUsers = userRepository.saveAll(users);
//            return GlobalResponseDTO
//                    .<NoPaginatedMeta, Void>builder()
//                    .meta(NoPaginatedMeta
//                            .builder()
//                            .status(Status.SUCCESS)
//                            .message(messageSourceUtil.getMessage(
//                                    SuccessMessage.User.IMPORTED_LIST,
//                                    savedUsers.size()
//                            ))
//                            .build()
//                    )
//                    .build();
//        } catch (IOException e) {
//            throw new RuntimeException(ErrorMessage.Import.PROCESS_EXCEL);
//        }
//    }
//
//    @Override
//    public GlobalResponseDTO<NoPaginatedMeta, Void> importCsvUser(
//            ImportDataRequestDTO request
//    ) {
//        if (ExcelUtil.notHasExcelFormat(request.file()))
//            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);
//
//        try {
//            List<User> users = CsvUtil.csvToUsers(request.file().getInputStream());
//            List<User> savedUsers = userRepository.saveAll(users);
//            return GlobalResponseDTO
//                    .<NoPaginatedMeta, Void>builder()
//                    .meta(NoPaginatedMeta
//                            .builder()
//                            .status(Status.SUCCESS)
//                            .message(messageSourceUtil.getMessage(
//                                    SuccessMessage.User.IMPORTED_LIST,
//                                    savedUsers.size()
//                            ))
//                            .build()
//                    )
//                    .build();
//        } catch (IOException ex) {
//            throw new RuntimeException(ErrorMessage.Import.PROCESS_CSV);
//        }
//    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> updateInfoUser(
            Integer userId,
            UpdateUserInfoRequest request
    ) {
        if (Objects.isNull(request))
            throw new NullRequestException(ErrorMessage.Request.NULL_REQUEST);

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();

        if (request.firstName() != null) foundUser.setFirstName(request.firstName());
        if (request.lastName() != null) foundUser.setLastName(request.lastName());
        if (request.avatar() != null) {
            String avatarImageUrl;
            avatarImageUrl = fileServiceClient.uploadAvatar(request.avatar());
            foundUser.setAvatarImageUrl(avatarImageUrl);
        }
        if (request.email() != null) foundUser.setEmail(request.email());
        if (request.phoneNumber() != null) foundUser.setPhoneNumber(request.phoneNumber());
        if (request.role() != null) foundUser.setRole(Role.valueOf(request.role()));
        foundUser.setLastUpdated(new Date(Instant.now().toEpochMilli()));
        User savedUser = userRepository.save(foundUser);

        return GlobalResponseDTO
                .<NoPaginatedMeta, FullUserResponseDTO>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.INFO_UPDATED))
                        .build()
                )
                .data(UserMapper.INSTANCE.toUserFullResponseDTO(savedUser))
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> updatePasswordUser(
            Integer userId,
            UpdateUserPasswordRequest request
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();

        if (!passwordEncoder.matches(request.oldPassword(), foundUser.getPassword()))
            throw new WrongPasswordException(ErrorMessage.User.WRONG_PASSWORD);

        if (!request.newPassword().equals(request.confirmNewPassword()))
            throw new MismatchedConfirmPasswordException(ErrorMessage.User.MISMATCHED_PASSWORD);

        foundUser.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(foundUser);

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.PASSWORD_UPDATED))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> softDeleteOneUser(
            Integer userId
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();
        foundUser.setDeleted(true);
        userRepository.save(foundUser);

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.SOFT_DELETED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> softDeleteListUsers(
            String userIds
    ) {
        List<Integer> ids = Arrays.stream(userIds.split(",")).map(Integer::valueOf).toList();
        List<User> foundUsers = userRepository.findAllById(ids);

        foundUsers.parallelStream().forEach(item -> item.setDeleted(true));

        userRepository.saveAll(foundUsers);

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.User.SOFT_DELETED_LIST,
                                foundUsers.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> hardDeleteOneUser(
            Integer id
    ) {
        Optional<User> found = userRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        userRepository.delete(found.get());

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.HARD_DELETED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> hardDeleteListUsers(
            String userIds
    ) {
        List<Integer> ids = Arrays.stream(userIds.split(",")).map(Integer::valueOf).toList();
        userRepository.deleteAllById(ids);

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.User.HARD_DELETED_LIST,
                                ids.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> restoreOneUser(
            Integer userId
    ) {
        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        found.get().setDeleted(false);
        userRepository.save(found.get());

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.User.RESTORED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> restoreListUsers(
            String userIds
    ) {
        List<Integer> ids = Arrays.stream(userIds.split(",")).map(Integer::valueOf).toList();
        List<User> foundUsers = userRepository.findAllById(ids);

        foundUsers.parallelStream().forEach(item -> item.setDeleted(false));

        userRepository.saveAll(foundUsers);

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.User.RESTORED_LIST,
                                foundUsers.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> resetPasswordOneUser(
            Integer userId
    ) {
        String newPassword = RandomUtil.randomPassword();

        Optional<User> found = userRepository.findById(userId);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        User foundUser = found.get();
        foundUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(foundUser);

        Map<String, Object> variables = Map.of(
                "username", foundUser.getUsername(),
                "newPassword", newPassword
        );

        ResetPasswordEvent mailData = ResetPasswordEvent.builder()
                                             .to(foundUser.getEmail())
                                             .subject("Khôi phục mật khẩu")
                                             .variables(variables)
                                             .build();

        kafkaProducer.sendObjectMessage("request-password", mailData);

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.User.RESET_PASSWORD_ONE,
                                foundUser.getUsername()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Void> resetPasswordListUsers(
            String userIds
    ) {
        List<Integer> ids = Arrays.stream(userIds.split(",")).map(Integer::valueOf).toList();
        List<User> foundUsers = userRepository.findAllById(ids);
        List<User> filteredUsers = foundUsers.stream().filter(item -> item.getEmail() != null && !item.getEmail().isBlank()).toList();
        filteredUsers.parallelStream().forEach(item -> {
            this.resetPasswordOneUser(item.getId());
        });

        return GlobalResponseDTO
                .<NoPaginatedMeta, Void>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.User.RESET_PASSWORD_LIST,
                                filteredUsers.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>> getListActiveUsers(
            PaginationRequestDTO request
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<User> page = request.keyword() == null
                          ? userRepository.getAllActiveUsers(pageable)
                          : userRepository.searchActiveUsers(request.keyword(), pageable);

        List<User> users = page.getContent();

        return GlobalResponseDTO
                .<PaginatedMeta, List<BriefUserResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(Pagination
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build()
                        )
                        .build()
                )
                .data(users.parallelStream()
                           .map(UserMapper.INSTANCE::toBriefUserResponseDTO)
                           .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>> getDeletedListUsers(
            PaginationRequestDTO request
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<User> page = request.keyword() == null
                          ? userRepository.getAllDeletedUsers(pageable)
                          : userRepository.searchDeletedUsers(request.keyword(), pageable);

        List<User> users = page.getContent();

        return GlobalResponseDTO
                .<PaginatedMeta, List<BriefUserResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(Pagination
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build())
                .data(users.parallelStream()
                           .map(UserMapper.INSTANCE::toBriefUserResponseDTO)
                           .collect(Collectors.toList()))
                .build();
    }
}
