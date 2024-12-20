package org.example.authservice.dto.home;

import lombok.Builder;

@Builder
public record ProductCountByBrandResponseDTO(
        String name,
        Integer count
) {
}
