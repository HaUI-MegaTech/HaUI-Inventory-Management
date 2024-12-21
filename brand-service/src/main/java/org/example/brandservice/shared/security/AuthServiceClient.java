package org.example.brandservice.shared.security;

import org.example.brandservice.dto.auth.ValidateTokenRequest;
import org.example.brandservice.dto.user.FullUserResponseDTO;
import org.example.brandservice.shared.global.GlobalResponseDTO;
import org.example.brandservice.shared.global.NoPaginatedMeta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service-client", url = "http://localhost:8089/api/v1")
public interface AuthServiceClient {

    @PostMapping(value = "/auth/validate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO>> validate(@RequestBody ValidateTokenRequest request);

}
