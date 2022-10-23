package com.kutli.userservice.customException;


public class ErrorMessage {
    public static final String USER_NOT_FOUND = "ERROR.USER.NOT_FOUND";

    private ErrorMessage() {
        throw new IllegalAccessError("Utility class");
    }
}
