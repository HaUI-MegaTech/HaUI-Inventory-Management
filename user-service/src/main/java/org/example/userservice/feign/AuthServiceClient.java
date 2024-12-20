package org.example.userservice.feign;

import org.example.userservice.dto.auth.ValidateTokenRequest;
import org.example.userservice.dto.user.FullUserResponseDTO;
import org.example.userservice.shared.global.GlobalResponseDTO;
import org.example.userservice.shared.global.NoPaginatedMeta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service-client", url = "http://localhost:8089/api/v1")
public interface AuthServiceClient {

    @PostMapping(value = "/auth/validate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullUserResponseDTO>> validate(@RequestBody ValidateTokenRequest request);

}
