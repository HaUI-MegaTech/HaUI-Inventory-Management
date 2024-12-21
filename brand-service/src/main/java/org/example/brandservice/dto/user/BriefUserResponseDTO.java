package org.example.brandservice.dto.user;

import lombok.Builder;
import org.example.brandservice.shared.entity.enums.Gender;
import org.example.brandservice.shared.entity.enums.Role;

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
