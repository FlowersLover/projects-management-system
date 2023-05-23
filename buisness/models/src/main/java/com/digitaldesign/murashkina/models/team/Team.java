package com.digitaldesign.murashkina.models.team;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@Table(name = "team")
public class Team {

    @Id
    @ManyToOne
    @JoinColumn(name = "project")
    public Project project;
    public TeamRole role;
    @ManyToOne
    @JoinColumn(name = "member")
    public Employee member;

}
