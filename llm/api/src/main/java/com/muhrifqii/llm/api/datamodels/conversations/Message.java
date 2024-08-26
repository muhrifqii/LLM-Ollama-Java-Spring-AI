package com.muhrifqii.llm.api.datamodels.conversations;

import lombok.Builder;

@Builder
public record Message(
        String id,
        String conversationId,
        Status status,
        String content,
        String createdAt) {

    public static enum Status {
        STREAMING, FINISHED
    }
}
