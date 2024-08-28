package com.muhrifqii.llm.api.usecases;

import reactor.core.publisher.Mono;

public interface SummarizerUsecase {
    Mono<String> makeTitle(String text);
}
