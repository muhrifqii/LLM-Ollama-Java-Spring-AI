package com.muhrifqii.llm.api.utils;

import lombok.experimental.UtilityClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;

@UtilityClass
public class DateUtils {

    public static String nowIsoString() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static long nowMillis() {
        return LocalDateTime.now()
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
    }

    public static String toIsoString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
