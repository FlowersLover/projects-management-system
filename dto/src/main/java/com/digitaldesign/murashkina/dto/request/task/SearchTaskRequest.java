package com.digitaldesign.murashkina.dto.request.task;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
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
public class SearchTaskRequest {
    private String taskName;
    private UUID executor;
    private UUID author;
    private Date deadline;
    private Date createdAt;
    private TaskStatus status;
}
