package com.digitaldesign.murashkina.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Изменение статуса задачи")
public class UpdateTaskStatusRequest {
    @Schema(description = "Статус")
    private String status;
}
