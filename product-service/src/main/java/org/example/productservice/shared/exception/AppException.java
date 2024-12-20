package org.example.productservice.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private String message;
}
