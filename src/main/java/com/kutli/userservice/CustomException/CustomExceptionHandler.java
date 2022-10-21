package com.kutli.userservice.CustomException;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessages> handleAppException(CustomException exception) {
        return responseErrorMessages(List.of(exception.getMessage()), exception.getError().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessages> handleValidationError(MethodArgumentNotValidException exception) {
        List<String> messages = exception.getBindingResult().getFieldErrors().stream().map(this::createFieldErrorMessage).collect(Collectors.toList());
        return responseErrorMessages(messages, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessages> handleException(Exception exception) {
        return responseErrorMessages(List.of("internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessages> responseErrorMessages(List<String> messages, HttpStatus status) {
        ErrorMessages errorMessages = new ErrorMessages();
        messages.forEach(errorMessages::append);
        return ResponseEntity.status(status).body(errorMessages);
    }

    private String createFieldErrorMessage(FieldError fieldError) {
        return "[" + fieldError.getField() +"] must be " + fieldError.getDefaultMessage() +
                ". your input: [" + fieldError.getRejectedValue() + "]";
    }
}
