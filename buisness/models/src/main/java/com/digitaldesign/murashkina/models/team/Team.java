package com.digitaldesign.murashkina.models.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Team {
    public Project project;
    public TeamRole role;
    public Employee member;
}
