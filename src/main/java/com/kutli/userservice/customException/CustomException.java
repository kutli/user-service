package com.kutli.userservice.customException;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class CustomException extends RuntimeException {

    private final String errorMessage;
    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private String field;
    private String value;

}
