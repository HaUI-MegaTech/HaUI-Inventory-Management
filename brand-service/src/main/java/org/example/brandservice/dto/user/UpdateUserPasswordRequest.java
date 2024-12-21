package org.example.brandservice.dto.user;

public record UpdateUserPasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
