package com.muhrifqii.llm.api.usecases;

import reactor.core.publisher.Flux;

public interface SemanticSearchUsecase<T> {

    Flux<T> search(String query);
}
