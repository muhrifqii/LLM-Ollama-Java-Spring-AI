package com.muhrifqii.llm.api.datamodels.conversations;

import lombok.Builder;

@Builder
public record Conversation(
        String id, String name, String description, String createdAt, String updatedAt) {
}
