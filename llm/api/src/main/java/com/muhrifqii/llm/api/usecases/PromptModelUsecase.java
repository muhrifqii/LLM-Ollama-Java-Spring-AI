package com.muhrifqii.llm.api.usecases;

import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PromptModelUsecase {

    Mono<Message> chat(String conversationID, UserMessage message);

    Flux<Message> streamChat(String conversationID, UserMessage message);
}
