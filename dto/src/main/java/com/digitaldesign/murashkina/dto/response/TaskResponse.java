package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Задача ответ")
public class TaskResponse {
    @Schema(description = "Идентификатор")
    private UUID id;
    @Schema(description = "Название")
    private String taskName;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Исполнитель")
    private UUID executor;
    @Schema(description = "Время выполнения в часах")
    private Integer hoursToCompleteTask;
    @Schema(description = "Автор")
    private UUID author;
    @Schema(description = "Крайний срок")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    @Schema(description = "Последние изменения")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastChanged;
    @Schema(description = "Дата и время создания")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    @Schema(description = "Статус")
    private TaskStatus status;
}
