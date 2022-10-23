package com.kutli.userservice.customException;


public class ErrorMessages {
    public static final String USER_NOT_FOUND = "ERROR.USER.NOT_FOUND";
    public static final String ERROR_UNIMPLEMENTED_METHOD = "ERROR.ERROR_UNIMPLEMENTED_METHOD";
    public static final String INVALID_QUERY_PARAMS = "ERROR.INVALID_QUERY_PARAMS";
    public static final String ID_NOT_FOUND = "ERROR.ID_NOT_FOUND";
    public static final String ID_DOESNT_MATCH = "ERROR.ID_DOESNT_MATCH";

    private ErrorMessages() {
        throw new IllegalAccessError("Utility class");
    }
}
