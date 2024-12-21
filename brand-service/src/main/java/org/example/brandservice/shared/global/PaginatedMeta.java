package org.example.brandservice.shared.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.brandservice.shared.global.Pagination;
import org.example.brandservice.shared.global.Status;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaginatedMeta(
        Status status,
        String message,
        Pagination pagination
) {
}
