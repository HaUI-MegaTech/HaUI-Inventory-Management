package org.example.authservice.dto.auth;

public record AuthenticationRequestDTO(
        String username,
        String password
) {
}
