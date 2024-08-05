package com.muhrifqii.llm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatClient chatClient;

    public Mono<String> singleChat(String message) {
        return Mono.just(chatClient.prompt().user(message).call().chatResponse());
    }
}
