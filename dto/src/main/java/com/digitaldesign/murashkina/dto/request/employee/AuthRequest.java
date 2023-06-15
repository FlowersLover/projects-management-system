package com.digitaldesign.murashkina.dto.request.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Авторизация сотрудника")
public class AuthRequest {

    @Schema(description = "Имя пользователя")
    private String account;
    @Schema(description = "Пароль")
    private String password;

}
