package com.muhrifqii.llm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.api.usecases.PromptModelUsecase;
import com.muhrifqii.llm.api.usecases.SummarizerUsecase;

@Service
@RequiredArgsConstructor
public class ChatService implements PromptModelUsecase, SummarizerUsecase {
    private final ChatClient chatClient;

    @Override
    public Mono<Message> chat(String conversationID, UserMessage message) {
        return Mono.fromCallable(() -> chatClient
                .prompt()
                .user(message.content())
                .call()
                .content())
                .map(response -> ChatHelper
                        .mapChatContent(conversationID, response));
    }

    @Override
    public Flux<Message> streamChat(String conversationID, UserMessage message) {
        return Flux.just(ChatHelper.newMessage(conversationID))
                .flatMap(source -> chatPromptStream(source, message));
    }

    @Override
    public Mono<String> makeTitle(String text) {
        final var template = """
                Make the given text into a single sentence of at most 5 words. "{text}"
                """;
        return Mono.fromCallable(() -> chatClient.prompt()
                .user(promptUser -> promptUser
                        .text(template)
                        .param("text", text))
                .call()
                .content());
    }

    private Flux<Message> chatPromptStream(Message source, UserMessage userMessage) {
        return Flux.just(new StringBuffer())
                .flatMap(buffer -> {
                    return chatClient.prompt()
                            .user(userMessage.content())
                            .stream()
                            .content()
                            .map(buffer::append)
                            .map(bufferedStr -> ChatHelper.mapChatContent(source, bufferedStr.toString()));
                });
    }

}
