package com.endava.mmarko.pia.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorReturnResponse resourceNotFoundError(ResourceNotFoundError e){
        return new ErrorReturnResponse(3, e.getMessage());
    }

    @ExceptionHandler(CreationConflictError.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorReturnResponse creationConflictError(CreationConflictError e){
        return new ErrorReturnResponse(4, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorReturnResponse userNotFoundError(){
        return new ErrorReturnResponse(1, "No such User found");
    }
}
