package org.example.brandservice.dto.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidateTokenRequest {
    private String token;
}
