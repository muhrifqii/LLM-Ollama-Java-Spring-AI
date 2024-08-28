package com.muhrifqii.llm.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ConversationRepository extends ReactiveCrudRepository<Conversation, String> {
    Flux<Conversation> findAllBy(Pageable pageable);
}
