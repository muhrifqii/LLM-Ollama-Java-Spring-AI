package com.muhrifqii.llm.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.repositories.ConversationRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;

    public Flux<Conversation> getConversations(int page, int size, String orderBy) {
        final var sort = Sort.by(Sort.Direction.DESC, orderBy);
        final var pageable = PageRequest.of(page, size, sort);
        return conversationRepository.findAllBy(pageable)
                .map(ChatHelper::mapConversation);
    }

    public Mono<Conversation> getConversation(String id) {
        return conversationRepository.findById(id)
                .map(ChatHelper::mapConversation);
    }
}
