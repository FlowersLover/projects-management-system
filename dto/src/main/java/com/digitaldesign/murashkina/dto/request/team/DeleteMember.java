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
@Schema(description = "Удалить участника проекта")
public class DeleteMember {
    @Schema(description = "Идентификатор проекта")
    private UUID project;
    @Schema(description = "Идентификатор участника проекта")
    private UUID member;
}

