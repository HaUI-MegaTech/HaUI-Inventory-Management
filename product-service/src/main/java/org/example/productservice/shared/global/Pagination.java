package org.example.productservice.shared.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Pagination(
        String keyword,
        Integer pageIndex,
        Short pageSize,
        Long totalItems,
        Integer totalPages
) {
}
