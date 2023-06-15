package com.digitaldesign.murashkina.dto.request.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Изменение пароля сотрудника")
public class UpdatePasswordRequest {
    @Pattern(regexp = "[A-zА-я1-9/!@%#$&*-_.,\"']*", message = "Пароль может содержать только строчные и прописные буквы, цифры и символы /\\!@%#$&*-_.,\"'")
    @Size(min = 8, max = 150, message = "Пароль может содержать от 8 до 150 символов")
    @NotBlank(message = "Пароль обязательное поле")
    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Подтверждение пароля")
    private String confirmPassword;
}
