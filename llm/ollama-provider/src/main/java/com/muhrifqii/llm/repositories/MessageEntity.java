package com.muhrifqii.llm.repositories;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import lombok.Builder;

@Table("ai_messages")
@Builder
public record MessageEntity(
        @Id String id,
        String coversationId,
        String content,
        String messageType,
        @CreatedDate LocalDateTime createdAt)
        implements Persistable<String>, Message {

    @Override
    @Nullable
    public String getId() {
        return id();
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

    @Override
    public String getContent() {
        return content();
    }

    @Override
    public Map<String, Object> getMetadata() {
        // todo: implement
        return Map.of();
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.fromValue(messageType);
    }

}
