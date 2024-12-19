package org.example.userservice.dto.user;

import lombok.Builder;
import org.example.userservice.shared.entity.enums.Gender;
import org.example.userservice.shared.entity.enums.Role;

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
