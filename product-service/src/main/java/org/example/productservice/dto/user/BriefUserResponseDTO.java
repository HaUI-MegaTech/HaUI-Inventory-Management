package org.example.productservice.dto.user;

import lombok.Builder;
import org.example.productservice.shared.entity.enums.Gender;
import org.example.productservice.shared.entity.enums.Role;

@Builder
public record BriefUserResponseDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String avatarImageUrl,
        String phoneNumber,
        Role role
) {}
