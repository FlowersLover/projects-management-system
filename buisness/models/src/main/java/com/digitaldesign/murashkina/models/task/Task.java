package com.digitaldesign.murashkina.models.task;

import com.digitaldesign.murashkina.dto.enums.TaskStatus;
import com.digitaldesign.murashkina.models.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 50)
    private String taskName;

    @Column(name = "description")
    @Size(min = 3, max = 150)
    private String description;

    @ManyToOne
    @JoinColumn(name = "executor")
    private Employee executor;

    @Column(name = "hours_to_comlete_task")
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

    public Task() {

    }
}
