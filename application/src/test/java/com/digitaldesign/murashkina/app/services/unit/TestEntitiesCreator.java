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
        return Employee.builder()
                .id(UUID.randomUUID())
                .account(account)
                .lastName("Test")
                .firstName("Test")
                .password("password12")
                .status(EStatus.ACTIVE)
                .position("tester")
                .build();
    }

    static Project createTestProject() {
        return Project.builder()
                .projectId(UUID.randomUUID())
                .projectStatus(ProjStatus.DRAFT)
                .description("description test")
                .projectName("testproject").build();
    }

    static Team createTeamTest(Employee employee, Project project) {
        TeamId teamId = TeamId.builder().member(employee).project(project).build();
        return Team.builder().role(TeamRole.ANALYST).teamId(teamId).build();
    }
}
