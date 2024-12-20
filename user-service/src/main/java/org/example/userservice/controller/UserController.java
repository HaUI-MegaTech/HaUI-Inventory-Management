package org.example.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.common.ImportDataRequestDTO;
import org.example.userservice.dto.user.*;
import org.example.userservice.service.UserService;
import org.example.userservice.shared.constant.Endpoint;
import org.example.userservice.shared.entity.User;
import org.example.userservice.shared.global.GlobalResponseDTO;
import org.example.userservice.shared.global.NoPaginatedMeta;
import org.example.userservice.shared.global.PaginatedMeta;
import org.example.userservice.shared.global.PaginationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(Endpoint.V1.User.GET_ONE)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO>> getOne(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOneUser(userId));
    }

    @PostMapping(Endpoint.V1.User.ADD_ONE)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BriefUserResponseDTO>> addOneUser(
            @RequestBody @Valid AddUserRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addOneUser(request));
    }

//    @PostMapping(Endpoint.V1.User.IMPORT_EXCEL)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> importExcelUser(
//            ImportDataRequestDTO request
//    ) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.importExcelUser(request));
//    }
//
//    @PostMapping(Endpoint.V1.User.IMPORT_CSV)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> importCsvUser(
//            @ParameterObject ImportDataRequestDTO request
//    ) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.importCsvUser(request));
//    }

    @PutMapping(Endpoint.V1.User.UPDATE_INFO)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO>> updateInfoUser(
            @PathVariable Integer userId,
            @ModelAttribute UpdateUserInfoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateInfoUser(userId, request));
    }

    @PatchMapping(Endpoint.V1.User.UPDATE_PASSWORD)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> updatePasswordUser(
            @PathVariable Integer userId,
            @RequestBody UpdateUserPasswordRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePasswordUser(userId, request));
    }


//    @Operation(summary = "Temporarily delete a User by Id")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
//                    //@ApiResponse(responseCode = "404", description = "Not Found")
//            }
//    )
//    @PatchMapping(Endpoint.V1.User.SOFT_DELETE_ONE)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> softDeleteOneUser(
//            @PathVariable String userIds
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.softDeleteOneUser(userId));
//    }

    @PatchMapping(Endpoint.V1.User.SOFT_DELETE_LIST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> softDeleteListUsers(
            @PathVariable String userIds
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.softDeleteListUsers(userIds));
    }


//    @Operation(summary = "Permanently delete a User by Id")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
//                    //@ApiResponse(responseCode = "404", description = "Not Found")
//            }
//    )
//    @DeleteMapping(Endpoint.V1.User.HARD_DELETE_ONE)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> hardDeleteOneUser(
//            @PathVariable Integer userId
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.hardDeleteOneUser(userId));
//    }

    @DeleteMapping(Endpoint.V1.User.HARD_DELETE_LIST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> hardDeleteListUsers(
            @PathVariable String userIds
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.hardDeleteListUsers(userIds));
    }


//    @Operation(summary = "Restore a User from deleted Users by Id")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
//                    //@ApiResponse(responseCode = "404", description = "Not Found")
//            }
//    )
//    @PatchMapping(Endpoint.V1.User.RESTORE_ONE)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> restoreOneUser(
//            @PathVariable Integer userId
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.restoreOneUser(userId));
//    }

    @PatchMapping(Endpoint.V1.User.RESTORE_LIST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> restoreListUsers(
            @PathVariable String userIds
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.restoreListUsers(userIds));
    }


//    @Operation(summary = "Reset User's password by Id")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    //@ApiResponse(responseCode = "403", description = "Forbidden"),
//                    //@ApiResponse(responseCode = "404", description = "Not Found")
//            }
//    )
//    @PatchMapping(Endpoint.V1.User.RESET_PASSWORD_ONE)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> resetPasswordOneUser(
//            @PathVariable Integer userId
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.resetPasswordOneUser(userId));
//    }

    @PatchMapping(Endpoint.V1.User.RESET_PASSWORD_LIST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> resetPasswordListUsers(
            @PathVariable String userIds
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.resetPasswordListUsers(userIds));
    }

    @GetMapping(Endpoint.V1.User.GET_ACTIVE_LIST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<
            GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>>
            > getActiveList(@ModelAttribute PaginationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getListActiveUsers(request));
    }

    @GetMapping(Endpoint.V1.User.GET_DELETED_LIST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>>> getDeletedList(
            @ModelAttribute PaginationRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getDeletedListUsers(request));
    }

    @PutMapping(Endpoint.V1.User.UPDATE_MY_INFO)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO>> updateMe(
            @AuthenticationPrincipal User user,
            @ModelAttribute UpdateUserInfoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateInfoUser(user.getId(), request));
    }
}
