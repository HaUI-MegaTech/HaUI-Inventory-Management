package org.example.userservice.shared.exception;

import lombok.RequiredArgsConstructor;
import org.example.userservice.shared.global.GlobalResponseDTO;
import org.example.userservice.shared.global.NoPaginatedMeta;
import org.example.userservice.shared.global.Status;
import org.example.userservice.shared.util.MessageSourceUtil;
import org.example.userservice.shared.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(AbsentRequiredFieldException.class)
    public ResponseEntity<?> handleBlankUsernameException(AbsentRequiredFieldException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(MismatchedConfirmPasswordException.class)
    public ResponseEntity<?> handleBlankPasswordException(MismatchedConfirmPasswordException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handleWrongPasswordException(WrongPasswordException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(InvalidRequestParamException.class)
    public ResponseEntity<?> handleInvalidRequestParamException(InvalidRequestParamException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(NullRequestException.class)
    public ResponseEntity<?> handleNullRequestException(NullRequestException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(MalformedFileException.class)
    public ResponseEntity<?> handleMalformedFileException(MalformedFileException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        return ResponseUtil.badRequest(GlobalResponseDTO.builder()
                                                        .meta(NoPaginatedMeta
                                                                      .builder()
                                                                      .status(Status.ERROR)
                                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                                      .build()
                                                        )
                                                        .build());
    }
    
}
