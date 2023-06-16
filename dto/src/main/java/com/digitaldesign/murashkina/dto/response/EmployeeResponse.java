package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сотрудник ответ")
public class EmployeeResponse {
    @Schema(description = "Идентификатор")
    private UUID id;
    @Schema(description = "Должность")
    private String position;
    @Schema(description = "Учетная запись")
    private String account;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Отчество")
    private String middleName;
    @Schema(description = "Эл-почта")
    private String email;
    @Schema(description = "Статус")
    private EStatus status;
}
