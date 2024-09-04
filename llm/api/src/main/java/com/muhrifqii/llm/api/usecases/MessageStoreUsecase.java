package com.muhrifqii.llm.api.usecases;

import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageStoreUsecase {
    Flux<Message> getMessages(String conversationId, String cursor);

    Mono<Message> saveUserMessage(UserMessage userMessage);

    Mono<Message> saveAssistantMessage(Message message);
}
