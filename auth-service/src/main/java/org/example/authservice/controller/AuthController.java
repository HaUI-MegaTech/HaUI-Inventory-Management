package org.example.authservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.auth.AuthenticationRequestDTO;
import org.example.authservice.dto.auth.RefreshTokenRequestDTO;
import org.example.authservice.dto.user.AddUserRequestDTO;
import org.example.authservice.service.AuthenticationService;
import org.example.authservice.shared.constant.Endpoint;
import org.example.authservice.shared.global.AuthData;
import org.example.authservice.shared.global.GlobalResponseDTO;
import org.example.authservice.shared.global.NoPaginatedMeta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping(Endpoint.V1.Auth.REGISTER)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, AuthData>> register(
            @RequestBody AddUserRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.register(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.AUTHENTICATE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, AuthData>> authenticate(
            @RequestBody AuthenticationRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.authenticate(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.REFRESH)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, AuthData>> refresh(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.refresh(request, servletRequest));
    }

    @PostMapping(Endpoint.V1.Auth.LOGOUT)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> logout(
            @RequestBody RefreshTokenRequestDTO request,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.logout(request, servletRequest));
    }
}
