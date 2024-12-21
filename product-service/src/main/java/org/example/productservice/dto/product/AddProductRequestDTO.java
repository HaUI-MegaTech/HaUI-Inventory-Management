package org.example.productservice.dto.product;

public record AddProductRequestDTO(
        String name,
        Float price
) {
}
