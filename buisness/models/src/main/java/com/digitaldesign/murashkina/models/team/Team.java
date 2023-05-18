package com.digitaldesign.murashkina.models.team;

import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;

import java.util.UUID;

public class Team {
    public UUID id;
    public Project project;
    public TeamRole role;
    public Employee employee;
}
