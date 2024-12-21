package org.example.productservice.dto.product;

import lombok.Builder;

@Builder
public record FilterProductRequestDTO(
        String brandIds,
        Float minPrice,
        Float maxPrice
) {
}
