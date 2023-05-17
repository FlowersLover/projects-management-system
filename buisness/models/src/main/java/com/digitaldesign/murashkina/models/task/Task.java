package com.digitaldesign.murashkina.models.task;

import com.digitaldesign.murashkina.models.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Task {
    private UUID id;
    private String taskName;
    private String description;
    private Employee executor;
    private Integer hoursToCompleteTask;
    private Employee author;
    private Date deadline;
    private Date lastChanged;
    private Date createdAt;

}
