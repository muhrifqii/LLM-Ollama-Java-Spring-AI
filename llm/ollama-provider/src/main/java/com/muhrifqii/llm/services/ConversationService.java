package com.muhrifqii.llm.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.muhrifqii.llm.Constants;
import com.muhrifqii.llm.api.datamodels.HandledError;
import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.repositories.ConversationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public Mono<Conversation> getOrCreateConversation(String id) {
        if (!StringUtils.hasText(id) || Constants.EMPTY_SLUG.equals(id)) {
            return createNewConversation();
        }
        return throwIfConversationIdNotExist(id)
                .switchIfEmpty(createNewConversation());
    }

    private Mono<Conversation> throwIfConversationIdNotExist(String id) {
        return getConversation(id)
                .switchIfEmpty(Mono.error(HandledError.notFound("Conversation not found")));
    }

    private Mono<Conversation> createNewConversation() {
        return conversationRepository
                .save(ChatHelper.newConversation(Constants.EMPTY_SLUG))
                .map(ChatHelper::mapConversation);
    }

    public Mono<String> updateConversation(String id, Conversation latest) {
        return Mono.justOrEmpty(id)
                .filter(StringUtils::hasText)
                .flatMap(conversationId -> conversationRepository.findById(conversationId))
                .map(conversation -> ChatHelper.updateConversation(conversation, latest))
                .flatMap(conversationRepository::save)
                .map(com.muhrifqii.llm.repositories.Conversation::id)
                .switchIfEmpty(Mono.error(HandledError.notFound("Conversation not found")));
    }
}
