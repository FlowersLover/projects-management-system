package com.digitaldesign.murashkina.models.metamodels;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Date;
import java.util.UUID;

public class Task_ {
    public static volatile SingularAttribute<Project, String> taskName;
    public static volatile SingularAttribute<Project, TaskStatus> status;
    public static volatile SingularAttribute<Project, UUID> executor;
    public static volatile SingularAttribute<Project, Date> createdAt;
    public static volatile SingularAttribute<Project, UUID> author;
    public static volatile SingularAttribute<Project, Date> deadline;


    public static final String TASK_NAME = "taskName";
    public static final String EXECUTOR = "executor";
    public static final String CREATED_AT = "createdAt";
    public static final String AUTHOR = "author";
    public static final String DEADLINE = "deadline";
    public static final String STATUS = "status";
}
