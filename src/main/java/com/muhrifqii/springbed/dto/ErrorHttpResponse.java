package com.muhrifqii.springbed.dto;

import lombok.Builder;

@Builder
public record ErrorHttpResponse(int status, int code, String message, String timestamp) {
}
