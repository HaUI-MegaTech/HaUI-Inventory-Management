package org.example.productservice.dto.common;

import lombok.Builder;

import java.util.List;

@Builder
public record ListIdsRequestDTO(
        List<Integer> ids
) {
}