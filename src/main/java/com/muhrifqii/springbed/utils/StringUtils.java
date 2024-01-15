package com.muhrifqii.springbed.utils;

import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {
    public static <T extends Enum<T>> T enumValueOf(String name, @Nonnull Class<T> type) {

        return Arrays.stream(type.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant"));
    }
}
