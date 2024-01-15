package com.muhrifqii.springbed.dto;

public record CreateBedRequest(
        String name,
        String description,
        String category
) {
}
