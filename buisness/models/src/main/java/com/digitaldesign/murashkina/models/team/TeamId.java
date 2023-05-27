package com.digitaldesign.murashkina.models.team;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "member")
    private Employee member;

    @ManyToOne
    @JoinColumn(name = "project")
    private Project project;
}