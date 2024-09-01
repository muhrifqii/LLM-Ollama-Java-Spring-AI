package com.muhrifqii.llm.api.usecases;

import com.muhrifqii.llm.api.datamodels.conversations.Conversation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConversationStoreUsecase {

    Flux<Conversation> getConversations(int page, int size, String orderBy);

    Mono<Conversation> getConversation(String id);

    Mono<Conversation> getOrCreateConversation(String id);

    Mono<Conversation> updateConversation(String id, Conversation latest);
}
