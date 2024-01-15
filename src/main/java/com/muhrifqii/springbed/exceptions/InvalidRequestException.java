package com.muhrifqii.springbed.exceptions;

import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final int code;

    public InvalidRequestException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public InvalidRequestException(String message, int code) {
        super(message);
        this.code = code;
    }
}
