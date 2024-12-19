package org.example.userservice.dto;

public record UpdateUserPasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
