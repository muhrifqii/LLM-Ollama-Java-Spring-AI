package com.muhrifqii.llm.services;

import java.util.Map;

import org.springframework.ai.chat.client.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.MessageAggregator;
import org.springframework.stereotype.Service;

import com.muhrifqii.llm.annotations.MemCachedChatMemory;
import com.muhrifqii.llm.api.usecases.MessageStoreUsecase;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MessageStoreAdvisor extends MessageChatMemoryAdvisor {

    private final MessageStoreUsecase messageStoreUsecase;
    private final MessageAggregator messageAggregator = new MessageAggregator();

    public MessageStoreAdvisor(
            @MemCachedChatMemory ChatMemory chatMemory,
            MessageStoreUsecase messageStoreUsecase) {
        super(chatMemory);
        this.messageStoreUsecase = messageStoreUsecase;
    }

    @Override
    public AdvisedRequest adviseRequest(AdvisedRequest request, Map<String, Object> context) {
        final var chatMemoryAdvisedRequest = super.adviseRequest(request, context);
        final var conversationId = doGetConversationId(context);

        if (!isConversationIdValid(conversationId)) {
            return chatMemoryAdvisedRequest;
        }

        log.debug("adviseRequest:{}", conversationId);
        Mono.just(new UserMessage(request.userText(), request.media()))
                .map(msg -> ChatHelper.mapFromAi(conversationId, msg))
                .flatMap(message -> messageStoreUsecase.saveMessage(message, true))
                .subscribe();

        return chatMemoryAdvisedRequest;
    }

    @Override
    public ChatResponse adviseResponse(ChatResponse response, Map<String, Object> context) {
        final var advisedResponse = super.adviseResponse(response, context);
        final var conversationId = doGetConversationId(context);

        if (!isConversationIdValid(conversationId)) {
            return advisedResponse;
        }

        log.debug("adviseResponse:{}", conversationId);
        Flux.fromStream(advisedResponse.getResults().stream())
                .map(g -> (Message) g.getOutput())
                .flatMap(msg -> saveAssistantMessage(conversationId, msg))
                .subscribe();

        return advisedResponse;
    }

    @Override
    public Flux<ChatResponse> adviseResponse(Flux<ChatResponse> fluxResponse, Map<String, Object> context) {
        return messageAggregator.aggregate(super.adviseResponse(fluxResponse, context), chatResponse -> {
            final var conversationId = doGetConversationId(context);

            if (!isConversationIdValid(conversationId)) {
                return;
            }

            log.debug("adviseResponse:{}", conversationId);
            Flux.fromStream(chatResponse.getResults().stream())
                    .map(g -> (Message) g.getOutput())
                    .flatMap(msg -> saveAssistantMessage(conversationId, msg))
                    .subscribe();
        });
    }

    private Mono<com.muhrifqii.llm.api.datamodels.conversations.Message> saveAssistantMessage(
            String conversationId,
            Message message) {
        return Mono.just(message)
                .map(msg -> ChatHelper.mapFromAi(conversationId, msg))
                .flatMap(msg -> messageStoreUsecase.saveMessage(msg, true));
    }

    private boolean isConversationIdValid(String conversationId) {
        return !DEFAULT_CHAT_MEMORY_CONVERSATION_ID.equals(conversationId);
    }

}
