package com.muhrifqii.llm.repositories;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Table("ai_messages")
@Getter
@Setter
@Accessors(fluent = true)
public class MessageEntity implements Persistable<String>, Message {
    @Id
    private String id;
    private String conversationId;
    private String content;
    private String messageType;
    @CreatedDate
    private LocalDateTime createdAt;
    @Transient
    private boolean newEntity;

    @Override
    @Nullable
    public String getId() {
        return id();
    }

    @Override
    public boolean isNew() {
        return createdAt == null || newEntity;
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
