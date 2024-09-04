package com.muhrifqii.llm.api.traits;

import com.muhrifqii.llm.api.usecases.PromptModelUsecase;
import com.muhrifqii.llm.api.usecases.SummarizerUsecase;

public interface ChatServiceTrait extends PromptModelUsecase, SummarizerUsecase {
}
