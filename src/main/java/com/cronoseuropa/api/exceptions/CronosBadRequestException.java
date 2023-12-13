package com.cronoseuropa.api.exceptions;

public class CronosBadRequestException extends CronosApiException {
    public CronosBadRequestException(String description, long code) {
        super(description, code);
    }
}
