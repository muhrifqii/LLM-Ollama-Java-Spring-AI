package com.muhrifqii.llm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.muhrifqii.llm.api.datamodels.ErrorResponse;
import com.muhrifqii.llm.api.datamodels.HandledError;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalError(Exception exception) {
        return handled(exception);
    }

    @ExceptionHandler(HandledError.NotFoundError.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handledNotFoundError(HandledError.NotFoundError error) {
        return handled(error);
    }

    @ExceptionHandler(HandledError.InvalidArgsError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handledNotFoundError(HandledError.InvalidArgsError error) {
        return handled(error);
    }

    private <T extends Throwable> ErrorResponse handled(T error) {
        final var msg = error.getMessage();
        log.error(msg, error);
        return new ErrorResponse(msg);
    }

    private <T extends HandledError> ErrorResponse handled(T error) {
        final var msg = error.getMessage();
        log.info(msg, error);
        return new ErrorResponse(msg);
    }
}
