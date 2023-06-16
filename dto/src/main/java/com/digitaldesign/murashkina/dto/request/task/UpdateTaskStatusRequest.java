package com.digitaldesign.murashkina.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Изменение статуса задачи")
public class UpdateTaskStatusRequest {
    @Schema(description = "Статус")
    private String status;
}
