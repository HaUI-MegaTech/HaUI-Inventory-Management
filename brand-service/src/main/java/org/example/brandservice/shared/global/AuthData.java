package org.example.brandservice.shared.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.brandservice.dto.user.FullUserResponseDTO;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthData(
        String refreshToken,
        String accessToken,
        FullUserResponseDTO loggedInUser
) {
}
