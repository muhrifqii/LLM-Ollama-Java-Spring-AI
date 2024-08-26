package com.muhrifqii.llm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.api.usecases.PromptModelUsecase;

@Service
@RequiredArgsConstructor
public class ChatService implements PromptModelUsecase {
    private final ChatClient chatClient;

    @Override
    public Mono<Message> chat(String conversationID, UserMessage message) {
        return Mono.defer(() -> {
            final var response = chatClient.prompt()
                    .user(message.content())
                    .call()
                    .content();
            return Mono.just(
                    ChatHelper.mapChatContent(conversationID, Message.Status.FINISHED, response));
        });
    }

    @Override
    public Flux<Message> streamChat(String conversationID, UserMessage message) {
        return chatClient.prompt()
                .user(message.content())
                .stream()
                .content()
                .map(content -> ChatHelper.mapChatContent(conversationID, Message.Status.STREAMING, content));
    }
}
