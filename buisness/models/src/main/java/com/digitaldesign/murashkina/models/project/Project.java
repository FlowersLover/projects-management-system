package com.digitaldesign.murashkina.models.project;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "project_name")
    @NonNull
    private String projectName;

    @Column(name = "description")
    private String description;

    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private ProjStatus projectStatus;

    public Project() {

    }
}
