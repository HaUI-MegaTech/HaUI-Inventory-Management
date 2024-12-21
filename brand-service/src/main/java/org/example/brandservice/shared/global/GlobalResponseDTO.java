package org.example.brandservice.shared.global;

import lombok.Builder;

@Builder
public record GlobalResponseDTO<Meta, Data>(
        Meta meta,
        Data data
) {
}
