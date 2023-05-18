package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.task.TaskStatus;

import java.util.Date;

public class TaskResponse {
    private String id;
    private String taskName;
    private String description;
    private Employee executor;
    private Integer hoursToCompleteTask;
    private Employee author;
    private Date deadline;
    private Date lastChanged;
    private Date createdAt;
    private TaskStatus status;
}
