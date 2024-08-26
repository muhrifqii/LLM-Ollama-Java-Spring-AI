package com.muhrifqii.llm.services;

import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.utils.DateUtils;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatHelper {
    public static Message mapChatContent(String conversationID, Message.Status status, String content) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .conversationId(conversationID)
                .status(status)
                .content(content)
                .createdAt(DateUtils.nowIsoString())
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
}
