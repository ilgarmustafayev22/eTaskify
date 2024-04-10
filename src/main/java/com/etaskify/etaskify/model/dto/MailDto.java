package com.etaskify.etaskify.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailDto {

    String to;
    String text;
    String subject;

}
