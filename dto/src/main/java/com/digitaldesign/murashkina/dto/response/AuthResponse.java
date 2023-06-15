package com.digitaldesign.murashkina.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Аутентификация")
public class AuthResponse {
    @Schema(description = "Barer token")
    private String jwtToken;
}
