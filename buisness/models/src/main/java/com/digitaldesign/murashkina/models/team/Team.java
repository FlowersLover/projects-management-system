package com.digitaldesign.murashkina.models.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@Table(name = "team")
@AllArgsConstructor
public class Team {
    @EmbeddedId
    private TeamId teamId;
    @Enumerated(EnumType.STRING)
    private TeamRole role;

    public Team() {

    }
}
