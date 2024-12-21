package org.example.productservice.dto;

import lombok.Builder;

@Builder
public record ImageDTO(
        Integer id,
        String url
) {
}
