package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TaskResponse {
    private String id;
    private String taskName;
    private String description;
    private UUID executor;
    private Integer hoursToCompleteTask;
    private UUID author;
    private Date deadline;
    private Date lastChanged;
    private Date createdAt;
    private TaskStatus status;
}
