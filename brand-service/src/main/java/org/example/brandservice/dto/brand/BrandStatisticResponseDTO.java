package org.example.brandservice.dto.brand;

import lombok.Builder;

@Builder
public record BrandStatisticResponseDTO(
        String name,
        Long value
) {
}
