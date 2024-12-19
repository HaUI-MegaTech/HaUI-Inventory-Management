package org.example.userservice.service;

import org.example.userservice.dto.common.ImportDataRequestDTO;
import org.example.userservice.dto.user.*;
import org.example.userservice.shared.global.GlobalResponseDTO;
import org.example.userservice.shared.global.NoPaginatedMeta;
import org.example.userservice.shared.global.PaginatedMeta;
import org.example.userservice.shared.global.PaginationRequestDTO;

import java.util.List;

public interface UserService {
    // Get
    GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> getOneUser(Integer userId);

    GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>> getListActiveUsers(PaginationRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefUserResponseDTO>> getDeletedListUsers(PaginationRequestDTO request);


    // Add
    GlobalResponseDTO<NoPaginatedMeta, BriefUserResponseDTO> addOneUser(AddUserRequestDTO request);


//    // Import
//    GlobalResponseDTO<NoPaginatedMeta, Void> importExcelUser(ImportDataRequestDTO request);
//
//    GlobalResponseDTO<NoPaginatedMeta, Void> importCsvUser(ImportDataRequestDTO request);


    // Update info
    GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO> updateInfoUser(
            Integer userId,
            UpdateUserInfoRequest request
    );


    // Update password
    GlobalResponseDTO<NoPaginatedMeta, Void> updatePasswordUser(
            Integer userId,
            UpdateUserPasswordRequest request
    );


    // Soft delete
    GlobalResponseDTO<NoPaginatedMeta, Void> softDeleteOneUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, Void> softDeleteListUsers(String userIds);


    // Hard delete
    GlobalResponseDTO<NoPaginatedMeta, Void> hardDeleteOneUser(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, Void> hardDeleteListUsers(String userIds);


    // Restore
    GlobalResponseDTO<NoPaginatedMeta, Void> restoreOneUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, Void> restoreListUsers(String userIds);


    // Reset password
    GlobalResponseDTO<NoPaginatedMeta, Void> resetPasswordOneUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, Void> resetPasswordListUsers(String userIds);
}
