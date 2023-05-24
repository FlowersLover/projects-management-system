package com.digitaldesign.murashkina.dto.request.task;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateTaskStatusRequest {
    private String status;
    private String id;
}
