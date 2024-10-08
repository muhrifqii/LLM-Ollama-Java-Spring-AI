package com.muhrifqii.llm.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import com.muhrifqii.llm.annotations.MemCachedChatMemory;

import lombok.extern.slf4j.Slf4j;

@Component
@MemCachedChatMemory
@Slf4j
public class MemcachedChatMemory implements ChatMemory {

    private Map<String, List<Message>> conversationHistory = new ConcurrentHashMap<>();

    @Override
    public void add(String conversationId, List<Message> messages) {
        if (!isConversationIdValid(conversationId)) {
            return;
        }

        log.debug("add:{} with {} messages", conversationId, messages.size());
        this.conversationHistory.putIfAbsent(conversationId, new ArrayList<>());
        this.conversationHistory.get(conversationId).addAll(messages);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        if (!isConversationIdValid(conversationId)) {
            return List.of();
        }

        log.debug("get:{}:{}", lastN, conversationId);
        List<Message> all = this.conversationHistory.get(conversationId);
        return all != null ? all.stream()
                .skip(Math.max(0, all.size() - lastN))
                .toList() : List.of();
    }

    @Override
    public void clear(String conversationId) {
        if (!isConversationIdValid(conversationId)) {
            return;
        }

        log.debug("clear:{}", conversationId);
        this.conversationHistory.remove(conversationId);
    }

    private boolean isConversationIdValid(String conversationId) {
        return !AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID.equals(conversationId);
    }

}
