package com.muhrifqii.llm.api.usecases;

import com.muhrifqii.llm.api.datamodels.conversations.Message;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageStoreUsecase {
    Flux<Message> getMessages(String conversationId, String cursor);

    Mono<Message> saveMessage(Message message, boolean newEntity);
}
