package com.muhrifqii.llm.configs;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.muhrifqii.llm.dto.ErrorHttpResponse;
import com.muhrifqii.llm.exceptions.InvalidRequestException;
import com.muhrifqii.llm.utils.DateUtils;

@Order(3)
@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorHttpResponse> error(InvalidRequestException err) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorHttpResponse.builder()
                        .code(err.getCode())
                        .message(err.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(DateUtils.now())
                        .build());
    }
}
