package com.muhrifqii.llm.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhrifqii.llm.api.datamodels.conversations.ConversationRequest;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.api.usecases.PromptModelUsecase;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/ai/ask-once")
@RequiredArgsConstructor
public class AskOnceChatController {

    private final PromptModelUsecase promptService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Up Up and Away!";
    }

    @PostMapping("/simple")
    public Mono<Message> chat(@RequestBody ConversationRequest input) {
        return promptService.chat(null, new UserMessage(input.message(), null));
    }

    @PostMapping(value = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Message> streamChat(@RequestBody ConversationRequest input) {
        return promptService.streamChat(null, new UserMessage(input.message(), null));
    }
}
