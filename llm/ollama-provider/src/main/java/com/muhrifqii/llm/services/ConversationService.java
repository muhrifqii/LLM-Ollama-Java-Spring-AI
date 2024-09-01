package com.muhrifqii.llm.services;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.muhrifqii.llm.Constants;
import com.muhrifqii.llm.api.datamodels.HandledError;
import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.api.traits.ConversationServiceTrait;
import com.muhrifqii.llm.repositories.ConversationRepository;
import com.muhrifqii.llm.repositories.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ConversationService implements ConversationServiceTrait {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @Override
    public Flux<Conversation> getConversations(int page, int size, String orderBy) {
        final var sort = Sort.by(Sort.Direction.DESC, orderBy);
        final var pageable = PageRequest.of(page, size, sort);
        return conversationRepository.findAllBy(pageable)
                .map(ChatHelper::mapConversation);
    }

    @Override
    public Mono<Conversation> getConversation(String id) {
        return conversationRepository.findById(id)
                .map(ChatHelper::mapConversation);
    }

    @Override
    public Mono<Conversation> getOrCreateConversation(String id) {
        if (!StringUtils.hasText(id) || Constants.EMPTY_SLUG.equals(id)) {
            return createNewConversation();
        }
        return throwIfConversationIdNotExist(id)
                .switchIfEmpty(createNewConversation());
    }

    @Override
    public Mono<Conversation> updateConversation(String id, Conversation latest) {
        return Mono.justOrEmpty(id)
                .filter(StringUtils::hasText)
                .flatMap(conversationId -> conversationRepository.findById(conversationId))
                .map(conversation -> ChatHelper.updateConversation(conversation, latest))
                .flatMap(conversationRepository::save)
                .map(ChatHelper::mapConversation)
                .switchIfEmpty(Mono.error(HandledError.notFound("Conversation not found")));
    }

    @Override
    public Flux<Message> getMessages(String conversationId, String cursor) {
        if (StringUtils.hasText(cursor)) {
            return messageRepository.findMessagesBeforeCursor(conversationId, cursor, Constants.PAGE_SIZE)
                    .map(ChatHelper::mapMessage);
        } else {
            return messageRepository.findLatestMessages(conversationId, Constants.PAGE_SIZE)
                    .map(ChatHelper::mapMessage);
        }
    }

    @Override
    public Mono<Message> saveAssistantMessage(Message message) {
        return null;
    }

    @Override
    public Mono<Message> saveUserMessage(UserMessage userMessage) {
        return null;
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

}
