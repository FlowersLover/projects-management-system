package com.digitaldesign.murashkina.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Задача")
public class TaskRequest {
    @Pattern(regexp = "[ A-Za-zА-я1-9/-]*")
    @Size(min = 3, max = 50, message = "Название задачи может содержать от 3 до 50 символов")
    @NotBlank(message = "Название задачи является обязательным полем")
    @Schema(description = "Название")
    private String taskName;

    @Size(min = 3, max = 150, message = "Описание задачи может содержать от 3 до 150 символов")
    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Исполнитель")
    private UUID executor;

    @Schema(description = "Количество часов на выполнение задачи")
    private Integer hoursToCompleteTask;

    @Future()
    @Schema(description = "Крайний срок")
    private Date deadline;

    @Schema(description = "Идентификатор проекта")
    private UUID project;

}
