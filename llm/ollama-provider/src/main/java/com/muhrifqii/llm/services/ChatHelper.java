package com.muhrifqii.llm.services;

import com.fasterxml.uuid.Generators;
import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.utils.DateUtils;
import com.muhrifqii.llm.repositories.MessageEntity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.ai.chat.messages.MessageType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatHelper {
    public static String generateId() {
        return Generators.timeBasedEpochGenerator()
                .generate()
                .toString();
    }

    public static com.muhrifqii.llm.repositories.Conversation newConversation(String name) {
        return new com.muhrifqii.llm.repositories.Conversation(
                generateId(),
                name,
                null,
                null,
                null);
    }

    public static Message newMessage(String conversationID) {
        return Message.builder()
                .id(generateId())
                .conversationId(conversationID)
                .messageType(MessageType.USER.getValue())
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
                .messageType(MessageType.ASSISTANT.getValue())
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

    public static Message mapMessage(MessageEntity source) {
        return Message.builder()
                .id(source.id())
                .conversationId(source.conversationId())
                .content(source.content())
                .messageType(source.messageType())
                .createdAt(DateUtils.toIsoString(source.createdAt()))
                .build();
    }

    public static MessageEntity mapMessage(Message source, boolean newEntity) {
        return new MessageEntity()
                .id(source.id())
                .conversationId(source.conversationId())
                .content(source.content())
                .messageType(source.messageType())
                .createdAt(DateUtils.fromIsoString(source.createdAt()))
                .newEntity(newEntity);
    }

    public static Message mapFromAi(String conversationId, org.springframework.ai.chat.messages.Message source) {
        return Message.builder()
                .id(generateId())
                .conversationId(conversationId)
                .content(source.getContent())
                .messageType(source.getMessageType().getValue())
                .createdAt(DateUtils.nowIsoString())
                .build();
    }

    public static Message mapFromAi(String conversationId, org.springframework.ai.chat.messages.UserMessage source) {
        return Message.builder()
                .id(generateId())
                .conversationId(conversationId)
                .content(source.getContent())
                .messageType(source.getMessageType().getValue())
                .createdAt(DateUtils.nowIsoString())
                .build();
    }

}
