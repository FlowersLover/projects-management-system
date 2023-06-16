package com.digitaldesign.murashkina.dto.request.project;

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
@Schema(description = "Проект")
public class ProjectRequest {
    @Pattern(regexp = "[А-яA-Za-z1-9 -]*", message = "Название проекта может содержать только буквы и цифры")
    @NotBlank(message = "Название проекта является обязательным полем")
    @Size(min = 3, max = 50, message = "Название проекта может содержать от 3 до 50 символов")
    @Schema(description = "Название проекта")
    private String projectName;

    @Size(min = 3, max = 150, message = "Описание проекта может содержать от 3 до 150 символов")
    @Schema(description = "Описание проекта")
    private String description;
}
