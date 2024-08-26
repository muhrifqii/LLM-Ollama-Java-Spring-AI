package com.muhrifqii.llm.api.datamodels.conversations;

import lombok.NonNull;

/**
 * Wrapper for multimodality media
 */
public record UserMultimodalityMedia(@NonNull String mimeType, byte[] content) {
}
