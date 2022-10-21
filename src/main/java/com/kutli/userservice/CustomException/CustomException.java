package com.kutli.userservice.CustomException;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final Error error;
    public CustomException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
