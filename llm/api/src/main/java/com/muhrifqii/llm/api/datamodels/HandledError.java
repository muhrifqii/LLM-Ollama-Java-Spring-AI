package com.muhrifqii.llm.api.datamodels;

import lombok.experimental.StandardException;

@StandardException
public abstract sealed class HandledError
        extends RuntimeException
        permits HandledError.NotFoundError, HandledError.InvalidArgsError {

    @StandardException
    public static final class NotFoundError extends HandledError {
    }

    @StandardException
    public static final class InvalidArgsError extends HandledError {
    }

    public static HandledError notFound(String message) {
        return new NotFoundError(message);
    }

    public static HandledError invalidArgs(String message) {
        return new InvalidArgsError(message);
    }
}
