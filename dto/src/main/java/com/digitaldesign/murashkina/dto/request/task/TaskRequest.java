package com.digitaldesign.murashkina.dto.request.task;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TaskRequest {
    private String taskName;
    private String description;
    private UUID executor;
    private Integer hoursToCompleteTask;
    private Date deadline;
    private UUID author;

}
