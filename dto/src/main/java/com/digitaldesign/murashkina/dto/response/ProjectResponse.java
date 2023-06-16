package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Проект ответ")
public class ProjectResponse {
    @Schema(description = "Идентификатор")
    private String projectId;
    @Schema(description = "Название")
    private String projectName;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Статус")
    private ProjStatus projectStatus;
}
