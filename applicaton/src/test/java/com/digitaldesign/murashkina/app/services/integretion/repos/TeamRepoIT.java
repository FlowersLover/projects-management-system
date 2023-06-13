package com.digitaldesign.murashkina.app.services.integretion.repos;

import com.digitaldesign.murashkina.app.services.TestConfig;
import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.enums.ProjStatus;
import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.dto.response.ProjectResponse;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@Import(TestConfig.class)
public class TeamRepoIT {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void deleteMember() {
        Employee employee = Employee.builder()
                .firstName("name").lastName("last").account("user1123")
                .position("position").password("qwerty12345").status(EStatus.ACTIVE).build();
        Employee dbemployee = employeeRepository.save(employee);

        Project project = Project.builder().projectName("test")
                .description("test test")
                .projectStatus(ProjStatus.TEST).build();
        Project dbproject = projectRepository.save(project);

        TeamId teamId = TeamId.builder()
                .project(dbproject)
                .member(dbemployee)
                .build();
        Team team = Team.builder().role(TeamRole.TESTER)
                .teamId(teamId).build();
        Team dbteam = teamRepository.save(team);
        Assertions.assertEquals(team.getTeamId(),dbteam.getTeamId());
        teamRepository.deleteMember(teamId.getProject(), teamId.getMember());
        Optional<Team> teamById = teamRepository.findById(teamId);
        Assertions.assertTrue(teamById.isEmpty());
    }
}
