package org.example.authservice.dto.user;

public record UpdateUserPasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
