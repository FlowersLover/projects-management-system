package com.digitaldesign.murashkina.models.project;

import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Size;
import java.util.UUID;
@Getter
@Setter
@Builder
@Entity
@Table(name = "project")
@AllArgsConstructor
@ToString
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "project_name")
    @NonNull
    @Size(min = 3, max = 50)
    private String projectName;

    @Column(name = "description")
    @Size(min = 3, max = 150)
    private String description;

    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private ProjStatus projectStatus;

    public Project() {

    }
}
