package com.digitaldesign.murashkina.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTaskStatusRequest {
    private String status;
    private String id;
}
