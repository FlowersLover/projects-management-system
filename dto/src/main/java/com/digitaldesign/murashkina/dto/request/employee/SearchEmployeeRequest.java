package com.digitaldesign.murashkina.dto.request.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Поиск сотрудника")
public class SearchEmployeeRequest {

    @Schema(description = "Имя пользователя")
    private String account;


    @Schema(description = "Фамилия")
    private String lastName;


    @Schema(description = "Имя")
    private String firstName;


    @Schema(description = "Отчество")
    private String middleName;

    @Schema(description = "Эл-почта")
    private String email;

}
