package com.muhrifqii.llm.services;

import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatHelper {
    public static com.muhrifqii.llm.repositories.Conversation newConversation(String name) {
        return new com.muhrifqii.llm.repositories.Conversation(
                UUID.randomUUID().toString(),
                name,
                null,
                null,
                null);
    }

    public static Message newMessage(String conversationID) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .conversationId(conversationID)
                .createdAt(DateUtils.nowIsoString())
                .build();
    }

    public static Message mapChatContent(String conversationID, String content) {
        return mapChatContent(newMessage(conversationID), content);
    }

    public static Message mapChatContent(Message source, String content) {
        return Message.builder()
                .id(source.id())
                .conversationId(source.conversationId())
                .createdAt(source.createdAt())
                .content(content)
                .build();
    }

    public static Conversation mapConversation(com.muhrifqii.llm.repositories.Conversation source) {
        return Conversation.builder()
                .id(source.id())
                .name(source.name())
                .description(source.description())
                .createdAt(DateUtils.toIsoString(source.createdAt()))
                .updatedAt(DateUtils.toIsoString(source.updatedAt()))
                .build();
    }

    public static com.muhrifqii.llm.repositories.Conversation updateConversation(
            com.muhrifqii.llm.repositories.Conversation source,
            Conversation latest) {
        if (Objects.equals(latest.name(), source.name())
                && Objects.equals(latest.description(), source.description())) {
            return source;
        }
        return new com.muhrifqii.llm.repositories.Conversation(
                source.id(),
                latest.name(),
                latest.description(),
                source.createdAt(),
                LocalDateTime.now());
    }

}
