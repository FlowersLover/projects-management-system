package com.digitaldesign.murashkina.dto.request.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Изменение сотрудника")
public class UpdateEmployeeRequest {
    @Pattern(regexp = "[А-яA-Za-z -]*")
    @NotBlank(message = "Должность является обязательным полем")
    @Size(min = 3, max = 50, message = "Должность может содержать от 3 до 50 символов")
    @Schema(description = "Должность")
    private String position;

    @Pattern(regexp = "[А-яA-Za-z -]*")
    @NotBlank(message = "Фамилия является обязательным полем")
    @Size(min = 3, max = 50, message = "Фамилия может содержать от 3 до 50 символов")
    @Schema(description = "Фамилия")
    private String lastName;

    @Pattern(regexp = "[А-яA-Za-z -]*")
    @Size(min = 3, max = 50, message = "Имя может содержать от 3 до 50 символов")
    @NotBlank(message = "Имя является обязательным полем")
    @Schema(description = "Имя")
    private String firstName;

    @Pattern(regexp = "[А-яA-Za-z -]*")
    @Size(min = 3, max = 50, message = "Отчество может содержать от 3 до 50 символов")
    @Schema(description = "Отчество")
    private String middleName;

    @Pattern(regexp = "[A-Za-z1-9/-/_/.]*")
    @Size(min = 3, max = 50, message = "Имя пользователя может содержать от 3 до 150 символов")
    @Schema(description = "Имя пользователя")
    private String account;

    @Email(message = "Должно иметь формат адреса электронной почты")
    @Schema(description = "Электронная почта")
    private String email;
}
