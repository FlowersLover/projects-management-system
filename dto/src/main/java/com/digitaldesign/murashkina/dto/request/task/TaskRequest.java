package com.digitaldesign.murashkina.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TaskRequest {
    private String taskName;
    private String description;
    private String executor;
    private Integer hoursToCompleteTask;
    private Date deadline;

}
