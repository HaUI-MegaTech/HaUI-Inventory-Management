package org.example.userservice.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DuplicateUsernameException extends RuntimeException {
    private String message;
}