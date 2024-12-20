package org.example.productservice.dto.user;

public record UpdateUserPasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
