package com.digitaldesign.murashkina.dto.request.task;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateTaskStatusRequest {
    private TaskStatus status;
}
