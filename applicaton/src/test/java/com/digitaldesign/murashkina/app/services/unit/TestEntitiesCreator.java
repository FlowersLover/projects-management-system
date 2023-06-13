package com.digitaldesign.murashkina.app.services.unit;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;

import java.util.UUID;

public class TestEntitiesCreator {
    static Employee createTestEmployee(String account) {
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .account(account)
                .lastName("Test")
                .firstName("Test")
                .password("password12")
                .status(EStatus.ACTIVE)
                .position("tester")
                .build();
        return employee;
    }
    static Project createTestProject() {
        Project project = Project.builder()
                .projectId(UUID.randomUUID())
                .projectStatus(ProjStatus.DRAFT)
                .description("description test")
                .projectName("testproject").build();
        return project;
    }
    static Team createTeamTest(Employee employee, Project project) {
        TeamId teamId = TeamId.builder().member(employee).project(project).build();
        Team team = Team.builder().role(TeamRole.ANALYST).teamId(teamId).build();
        return team;
    }
}
