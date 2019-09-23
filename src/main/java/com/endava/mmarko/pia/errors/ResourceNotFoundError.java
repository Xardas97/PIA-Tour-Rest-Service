package com.endava.mmarko.pia.errors;

public class ResourceNotFoundError extends RuntimeException {
  public ResourceNotFoundError(String message) {
    super(message);
  }

  public ResourceNotFoundError() {
    super("No such resource exists.");
  }
}
