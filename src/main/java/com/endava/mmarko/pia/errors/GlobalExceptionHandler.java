package com.endava.mmarko.pia.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final int USER_NOT_FOUND_ERROR_CODE = 1;
  private static final int NOT_FOUND_ERROR_CODE = 3;
  private static final int CONFLICT_ERROR_CODE = 4;

  @ExceptionHandler(ResourceNotFoundError.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorReturnResponse resourceNotFoundError(ResourceNotFoundError e) {
    return new ErrorReturnResponse(NOT_FOUND_ERROR_CODE, e.getMessage());
  }

  @ExceptionHandler(CreationConflictError.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorReturnResponse creationConflictError(CreationConflictError e) {
    return new ErrorReturnResponse(CONFLICT_ERROR_CODE, e.getMessage());
  }

  @ExceptionHandler(UserNotFoundError.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorReturnResponse userNotFoundError() {
    return new ErrorReturnResponse(USER_NOT_FOUND_ERROR_CODE, "No such User found");
  }
}
