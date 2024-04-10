package com.etaskify.etaskify.model.dto;

import com.etaskify.etaskify.model.TokenPair;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationDto {

    Long userId;
    TokenPair tokenPair;

}
