package org.example.userservice.model.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GlobalResponseDTO<Meta, Data>(
        Meta meta,
        Data data
) {
}