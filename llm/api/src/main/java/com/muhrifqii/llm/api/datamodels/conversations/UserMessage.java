package com.muhrifqii.llm.api.datamodels.conversations;

public record UserMessage(
        String content,
        UserMultimodalityMedia media) {
}
