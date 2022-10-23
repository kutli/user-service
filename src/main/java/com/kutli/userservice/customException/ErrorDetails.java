package com.kutli.userservice.customException;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDetails {

    @Builder.Default
    private Date timestamp = new Date();
    private String field;
    private String value;
    private String errorMessage;
    @Builder.Default
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Set<String> body = new HashSet<>();;


    public void appendBody(String message) {
        body.add(message);
    }

    @Override
    public String toString() {
        return "ErrorDetails [timestamp=" + timestamp + ", field=" + field + ", value=" + value + ", errorMessage="
                + errorMessage + "]";
    }
}
