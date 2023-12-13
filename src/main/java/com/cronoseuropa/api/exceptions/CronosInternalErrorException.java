package com.cronoseuropa.api.exceptions;

public class CronosInternalErrorException extends CronosApiException {
    public CronosInternalErrorException(String description, long code) {
        super(description, code);
    }
}
