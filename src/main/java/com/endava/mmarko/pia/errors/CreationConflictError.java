package com.endava.mmarko.pia.errors;

public class CreationConflictError extends RuntimeException {
  public CreationConflictError() {
    super("Creation failed.");
  }

  public CreationConflictError(String message) {
    super(message);
  }
}
