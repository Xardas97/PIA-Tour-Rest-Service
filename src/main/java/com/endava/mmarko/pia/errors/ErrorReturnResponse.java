package com.endava.mmarko.pia.errors;

public class ErrorReturnResponse {
    private int code;
    private String message;

    public ErrorReturnResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
