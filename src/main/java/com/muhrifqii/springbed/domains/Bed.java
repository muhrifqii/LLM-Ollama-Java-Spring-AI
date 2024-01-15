package com.muhrifqii.springbed.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Table(name = "beds")
public record Bed(
        @Id @JsonIgnore
        Long tid,
        String identifier,
        String name,
        String description,
        Category category,
        @CreatedDate
        LocalDateTime createdAt,
        @LastModifiedDate
        LocalDateTime updatedAt
) {
    public enum Category {
        FRAMES_AND_LEGS, SPRING_MATTRESS, FOAM_MATTRESS, MATTRESS_PAD
    }
}
