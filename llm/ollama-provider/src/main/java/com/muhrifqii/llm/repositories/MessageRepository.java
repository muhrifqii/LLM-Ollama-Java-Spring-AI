package com.muhrifqii.llm.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import org.springframework.data.r2dbc.repository.Query;

@Repository
public interface MessageRepository extends ReactiveCrudRepository<MessageEntity, String> {

    @Query("""
            SELECT * FROM ai_messages
            WHERE coversation_id = :conversationId
            AND id < :cursor
            ORDER BY id DESC
            LIMIT :limit
            """)
    Flux<MessageEntity> findMessagesBeforeCursor(
            String conversationId,
            String cursor,
            int limit);

    @Query("""
            SELECT * FROM ai_messages
            WHERE coversation_id = :conversationId
            ORDER BY id DESC
            LIMIT :limit
            """)
    Flux<MessageEntity> findLatestMessages(
            String conversationId,
            int limit);
}
