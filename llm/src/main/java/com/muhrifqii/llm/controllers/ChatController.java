package com.muhrifqii.llm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhrifqii.llm.dto.ChatMessageRequest;
import com.muhrifqii.llm.services.ChatService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/simple")
    public Mono<String> chat(@RequestBody ChatMessageRequest input) {
        return chatService.singleChat(input.message());
    }

    @PostMapping("/full")
    public Mono<ChatResponse> chatPromptFullResponse(@RequestBody ChatMessageRequest input) {
        return chatService.singleChatWithResponse(input.message());
    }
}
