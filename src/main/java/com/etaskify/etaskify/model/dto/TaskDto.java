package com.etaskify.etaskify.model.dto;

import com.etaskify.etaskify.model.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {

    Long organizationId;

    @NotBlank
    String title;

    @NotBlank
    String description;

    LocalDateTime deadline;

    @NotNull
    TaskStatus status;

}
