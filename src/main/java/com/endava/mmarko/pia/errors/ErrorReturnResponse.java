package com.endava.mmarko.pia.errors;

public class ErrorReturnResponse {
  private final int code;
  private final String message;

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
