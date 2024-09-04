package com.muhrifqii.llm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.AdvisorSpec;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import com.muhrifqii.llm.Constants;
import com.muhrifqii.llm.api.datamodels.conversations.Message;
import com.muhrifqii.llm.api.datamodels.conversations.UserMessage;
import com.muhrifqii.llm.api.traits.ChatServiceTrait;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class ChatService implements ChatServiceTrait {

    private final ChatClient chatClient;

    @Override
    public Mono<Message> chat(String conversationID, UserMessage message) {
        return Mono.fromSupplier(() -> chatClient
                .prompt()
                .user(message.content())
                .advisors(advSpec -> chatMemoryAdvisorSpec(advSpec, conversationID))
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
                            .advisors(advSpec -> chatMemoryAdvisorSpec(advSpec, source.conversationId()))
                            .stream()
                            .content()
                            .map(buffer::append)
                            .map(bufferedStr -> ChatHelper.mapChatContent(source,
                                    bufferedStr.toString()));
                });
    }

    private void chatMemoryAdvisorSpec(AdvisorSpec advisorSpec, String conversationId) {
        if (Constants.EMPTY_SLUG.equals(conversationId) || conversationId == null) {
            return;
        }
        advisorSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId);
    }

}
