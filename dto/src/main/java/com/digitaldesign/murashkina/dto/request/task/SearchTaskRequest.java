package com.digitaldesign.murashkina.dto.request.task;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "Поиск задачи")
public class SearchTaskRequest {
    @Schema(description = "Название")
    private String taskName;
    @Schema(description = "Исполнитель")
    private UUID executor;
    @Schema(description = "Автор")
    private UUID author;
    @Schema(description = "Крайний срок")
    private Date deadline;
    @Schema(description = "Дата создания")
    private Date createdAt;
    @Schema(description = "Статус")
    private TaskStatus status;
}
