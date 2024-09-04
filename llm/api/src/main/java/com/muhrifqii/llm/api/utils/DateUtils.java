package com.muhrifqii.llm.api.utils;

import lombok.experimental.UtilityClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.time.ZoneOffset;

@UtilityClass
public class DateUtils {

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String nowIsoString() {
        return DateTimeFormatter.ISO_DATE_TIME
                .format(now());
    }

    public static long nowMillis() {
        return now()
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
    }

    public static String toIsoString(LocalDateTime date) {

        return Optional.ofNullable(date)
                .map(DateTimeFormatter.ISO_DATE_TIME::format)
                .orElse("");
    }

    public static LocalDateTime fromIsoString(String date) {
        return Optional.ofNullable(date)
                .map(DateTimeFormatter.ISO_DATE_TIME::parse)
                .map(LocalDateTime::from)
                .orElse(null);
    }
}
