package com.digitaldesign.murashkina.models.task;

import com.digitaldesign.murashkina.models.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Setter
@Getter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String taskName;
    private String description;
    @ManyToOne
    @JoinColumn(name = "executor")
    private Employee executor;
    private Integer hoursToCompleteTask;
    @ManyToOne
    @JoinColumn(name = "author")
    private Employee author;
    private Date deadline;
    private Date lastChanged;
    private Date createdAt;
    private TaskStatus status;

}
