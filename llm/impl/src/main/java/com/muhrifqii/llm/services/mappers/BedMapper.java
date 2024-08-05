package com.muhrifqii.llm.services.mappers;

import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import com.muhrifqii.llm.domains.Bed;
import com.muhrifqii.llm.dto.CreateBedRequest;
import com.muhrifqii.llm.exceptions.InvalidRequestException;
import com.muhrifqii.llm.utils.StringUtils;

import java.util.UUID;

@NoArgsConstructor
@Component
public final class BedMapper {
    public Bed map(@Nonnull CreateBedRequest bedRequest) {
        try {
            return Bed.builder()
                    .identifier(UUID.randomUUID().toString())
                    .name(bedRequest.name())
                    .description(bedRequest.description())
                    .category(StringUtils.enumValueOf(bedRequest.category(), Bed.Category.class))
                    .build();
        } catch (IllegalArgumentException err) {
            if (err.getMessage().contains("enum")) {
                throw new InvalidRequestException("Invalid category", 21);
            } else {
                throw new RuntimeException(err);
            }
        }
    }
}
