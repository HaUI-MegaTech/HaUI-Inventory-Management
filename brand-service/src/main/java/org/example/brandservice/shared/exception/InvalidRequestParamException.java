package org.example.brandservice.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidRequestParamException extends RuntimeException {
    private String message;
}