package com.muhrifqii.llm.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.muhrifqii.llm.api.datamodels.ConversationRequest;
import com.muhrifqii.llm.api.datamodels.conversations.Conversation;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.services.ChatService;
import com.muhrifqii.llm.services.ConversationService;

@RestController
@RequestMapping("/ai/conversations")
@RequiredArgsConstructor
public class ConversationalChatController {

    private final ChatService chatService;
    private final ConversationService conversationService;

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
        return chatService.chat(id, new UserMessage(input.message(), null));
    }

    @PostMapping("/{id}/chat-stream")
    public Flux<Message> streamChat(@PathVariable String id, @RequestBody ConversationRequest input) {
        return chatService.streamChat(id, new UserMessage(input.message(), null));
    }

}
