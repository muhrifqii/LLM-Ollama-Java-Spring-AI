package com.muhrifqii.llm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier
@Target({ ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MemCachedChatMemory {
}
