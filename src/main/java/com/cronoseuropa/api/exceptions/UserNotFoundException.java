package com.cronoseuropa.api.exceptions;

public class UserNotFoundException extends CronosApiException {
    public UserNotFoundException() {
        super("The user was not found", 2001);
    }
}
