package com.muhrifqii.llm.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.muhrifqii.llm.Constants;
import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.api.datamodels.conversations.ConversationRequest;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.api.traits.ChatServiceTrait;
import com.muhrifqii.llm.api.traits.ConversationServiceTrait;

@RestController
@RequestMapping("/ai/conversations")
@RequiredArgsConstructor
@Slf4j
public class ConversationalChatController {

    private final ChatServiceTrait chatService;
    private final ConversationServiceTrait conversationService;

    @GetMapping()
    public Flux<Conversation> getConversations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(name = "order_by", defaultValue = "updated_at") String orderBy) {

        return conversationService.getConversations(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Mono<Conversation> getConversation(@PathVariable String id) {
        return conversationService.getConversation(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Conversation not found")));
    }

    @PostMapping("/{id}/chat-and-wait")
    public Mono<Message> chatAndWait(@PathVariable String id, @RequestBody ConversationRequest input) {
        final var userMessage = new UserMessage(input.message(), null);
        return conversationService.getOrCreateConversation(id)
                .doOnNext(conversation -> makeTitle(input, conversation))
                .flatMap(conversation -> chatService
                        .chat(conversation.id(), userMessage));
    }

    @PostMapping(value = "/{id}/chat-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Message> streamChat(@PathVariable String id, @RequestBody ConversationRequest input) {
        final var userMessage = new UserMessage(input.message(), null);
        return conversationService.getOrCreateConversation(id)
                .doOnNext(conversation -> makeTitle(input, conversation))
                .flatMapMany(conversation -> chatService
                        .streamChat(conversation.id(), userMessage));
    }

    private void makeTitle(ConversationRequest input, Conversation conversation) {
        if (!Constants.EMPTY_SLUG.equals(conversation.name())) {
            return;
        }

        chatService.makeTitle(input.message())
                .map(title -> Conversation.builder()
                        .name(title)
                        .build())
                .flatMap(latest -> conversationService
                        .updateConversation(conversation.id(), latest))
                .timeout(Duration.ofSeconds(60))
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }

}
