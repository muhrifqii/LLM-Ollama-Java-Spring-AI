package com.muhrifqii.llm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;

    public Mono<String> singleChat(String message) {
        return Mono.just(chatClient.generate(message));
    }

    public Mono<ChatResponse> singleChatWithResponse(String message) {
        return Mono.just(chatClient.generate(new Prompt(message)));
    }
}
