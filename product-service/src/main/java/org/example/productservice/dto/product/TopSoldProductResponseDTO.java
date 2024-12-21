package org.example.productservice.dto.product;

import lombok.Builder;

@Builder
public record TopSoldProductResponseDTO(
        Integer id,
        String name,
        Float newPrice,
        Integer totalSold,
        String mainImageUrl,
        Double revenue
) {
}
