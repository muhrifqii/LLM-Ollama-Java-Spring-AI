package com.muhrifqii.llm.repositories;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

@Table("ai_conversations")
public record Conversation(
        @Id String id,
        @Column(value = "a_name") String name,
        @Column(value = "a_description") String description,
        @CreatedDate LocalDateTime createdAt,
        @LastModifiedDate LocalDateTime updatedAt) implements Persistable<String> {

    @Override
    @Nullable
    public String getId() {
        return id();
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

}
