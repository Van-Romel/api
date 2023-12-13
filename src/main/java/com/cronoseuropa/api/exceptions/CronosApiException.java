package com.cronoseuropa.api.exceptions;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CronosApiException extends RuntimeException  implements Serializable  {

    private final String description;
    private final long code;

    protected CronosApiException(String description, long code) {
        if (null == description || description.isBlank()) description = "Internal Error, please contact the support";
        if (code < 1000) code = -1;
        this.description = description;
        this.code = code;
    }
}
