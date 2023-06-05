package com.digitaldesign.murashkina.dto.request.team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Создать участника проекта")
public class TeamDto {
    @Schema(description = "Идентификатор проекта")
    private UUID project;

    @Schema(description = "Роль сотрудника в команде")
    private String role;
    @Schema(description = "Идентификатор участника команды")
    private UUID member;
}
