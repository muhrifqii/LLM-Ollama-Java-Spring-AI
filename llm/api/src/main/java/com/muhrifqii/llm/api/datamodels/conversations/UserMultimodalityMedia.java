package com.muhrifqii.llm.api.datamodels.conversations;

import org.springframework.util.MimeType;

import lombok.NonNull;

/**
 * Wrapper for multimodality media
 */
public record UserMultimodalityMedia(@NonNull MimeType mimeType, byte[] content) {
}
