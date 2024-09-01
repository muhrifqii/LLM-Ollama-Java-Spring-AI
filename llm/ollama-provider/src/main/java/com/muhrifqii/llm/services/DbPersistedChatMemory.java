package com.muhrifqii.llm.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import com.muhrifqii.llm.repositories.ConversationRepository;
import com.muhrifqii.llm.repositories.MessageEntity;
import com.muhrifqii.llm.repositories.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DbPersistedChatMemory implements ChatMemory {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @Override
    public void add(String conversationId, List<Message> messages) {
        log.debug("ChatMemory:add:{}", conversationId);
        Mono.fromCallable(() -> messages.stream()
                .map(msg -> ChatHelper.mapFromAi(conversationId, msg))
                .collect(Collectors.toList()))
                .flatMapMany(messageRepository::saveAll)
                .then()
                .toFuture()
                .join();
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return messageRepository.findLatestMessages(conversationId, lastN)
                .map(this::map)
                .collectList()
                .toFuture()
                .join();
    }

    @Override
    public void clear(String conversationId) {
        conversationRepository.deleteById(conversationId).block();
    }

    private Message map(MessageEntity source) {
        return source;
    }
}
