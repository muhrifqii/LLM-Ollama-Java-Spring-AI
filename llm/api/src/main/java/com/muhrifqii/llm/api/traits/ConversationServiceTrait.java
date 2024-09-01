package com.muhrifqii.llm.api.traits;

import com.muhrifqii.llm.api.usecases.ConversationStoreUsecase;
import com.muhrifqii.llm.api.usecases.MessageStoreUsecase;

public interface ConversationServiceTrait extends ConversationStoreUsecase, MessageStoreUsecase {
}
