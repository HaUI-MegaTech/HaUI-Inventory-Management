package org.example.productservice.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NullRequestException extends RuntimeException {
    private String message;
}
