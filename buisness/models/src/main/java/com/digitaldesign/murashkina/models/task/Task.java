package com.digitaldesign.murashkina.models.task;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "task")
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "task_name")
    @NonNull
    private String taskName;

    @Column(name = "description")
    @Size(min = 3, max = 150)
    private String description;

    @ManyToOne
    @JoinColumn(name = "executor")
    private Employee executor;

    @Column(name = "hours_to_complete_task")
    @NonNull
    private Integer hoursToCompleteTask;

    @ManyToOne
    @JoinColumn(name = "author")
    @NonNull
    private Employee author;

    @NonNull
    private Date deadline;

    @Column(name = "last_changed")
    private Date lastChanged;

    @Column(name = "created_at")
    private Date createdAt;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project")
    @NonNull
    private Project projectId;

    public Task() {

    }
}
