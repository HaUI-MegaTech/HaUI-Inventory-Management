package org.example.productservice.shared.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.example.productservice.shared.global.Status;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NoPaginatedMeta(
        Status status,
        String message
) {
}
