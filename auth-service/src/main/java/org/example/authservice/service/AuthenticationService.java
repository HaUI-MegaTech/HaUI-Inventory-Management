package org.example.authservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.authservice.dto.auth.AuthenticationRequestDTO;
import org.example.authservice.dto.auth.RefreshTokenRequestDTO;
import org.example.authservice.dto.user.AddUserRequestDTO;
import org.example.authservice.shared.global.AuthData;
import org.example.authservice.shared.global.GlobalResponseDTO;
import org.example.authservice.shared.global.NoPaginatedMeta;

public interface AuthenticationService {
    GlobalResponseDTO<NoPaginatedMeta, AuthData> register(AddUserRequestDTO request, HttpServletRequest servletRequest);

    GlobalResponseDTO<NoPaginatedMeta, AuthData> authenticate(AuthenticationRequestDTO request, HttpServletRequest servletRequest);

    GlobalResponseDTO<NoPaginatedMeta, AuthData> refresh(RefreshTokenRequestDTO request, HttpServletRequest servletRequest);

    GlobalResponseDTO<NoPaginatedMeta, Void> logout(RefreshTokenRequestDTO request, HttpServletRequest servletRequest);
}
