package com.muhrifqii.llm.api.datamodels.conversations;

import lombok.Builder;

@Builder
public record Message(
        String id,
        String conversationId,
        String content,
        String createdAt) {
}
