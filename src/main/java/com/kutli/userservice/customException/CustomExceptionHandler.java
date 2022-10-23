package com.kutli.userservice.customException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(CustomException exception) {
        ErrorDetails errorMessages = ErrorDetails.builder()
                .errorMessage(exception.getErrorMessage())
                .field(exception.getField())
                .value(exception.getValue())
                .build();
        return ResponseEntity.status(exception.getHttpStatus()).body(errorMessages);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationError(MethodArgumentNotValidException exception) {
        List<String> messages = exception.getBindingResult().getFieldErrors().stream().map(this::createFieldErrorMessage).collect(Collectors.toList());
        return responseErrorDetails(messages, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException e) {
        return responseErrorDetails(Collections.singletonList(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception) {
        return responseErrorDetails(Collections.singletonList("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException e) {
        return responseErrorDetails(Collections.singletonList(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorDetails> responseErrorDetails(List<String> messages, HttpStatus status) {
        ErrorDetails errorMessages = ErrorDetails.builder().build();
        messages.forEach(errorMessages::appendBody);
        return ResponseEntity.status(status).body(errorMessages);
    }

    private String createFieldErrorMessage(FieldError fieldError) {
        return "[" + fieldError.getField() +"] must be " + fieldError.getDefaultMessage() +
                ". your input: [" + fieldError.getRejectedValue() + "]";
    }
}
