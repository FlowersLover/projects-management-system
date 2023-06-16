package com.digitaldesign.murashkina.dto.request.project;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(description = "Поиск проекта")
public class SearchProjRequest {
    @Schema(description = "Идентификатор")
    private UUID id;
    @Schema(description = "Список статусов")
    private List<ProjStatus> statuses;
    @Schema(description = "Название проекта")
    private String projectName;
}
