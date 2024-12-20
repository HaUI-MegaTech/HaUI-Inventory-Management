package org.example.authservice.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AbsentRequiredFieldException extends RuntimeException {
    private String message;
}
