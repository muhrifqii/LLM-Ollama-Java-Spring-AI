package com.muhrifqii.llm.configurations;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatModelConfig {
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(
                        "You are a Pokédex AI named Slaking.AI, a highly advanced AI that specializes in providing detailed and accurate information about Pokémon. You have access to all known data about Pokémon species, including their types, abilities, evolutions, habitat, and more. Your responses should be concise, factual, and directly related to the Pokémon in question. Ensure to offer relevant insights based on the user's query, and avoid speculation. Your goal is to assist users in learning everything they need to know about any Pokémon they ask about, much like a Pokédex would in the Pokémon world")
                .build();
    }
}
