package com.digitaldesign.murashkina.app.services.unit;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import com.digitaldesign.murashkina.dto.enums.TeamRole;
import com.digitaldesign.murashkina.dto.request.team.DeleteMember;
import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.models.employee.Employee;
import com.digitaldesign.murashkina.models.project.Project;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import com.digitaldesign.murashkina.services.TeamService;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeDeletedException;
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.team.EmployeeAlreadyInTeamException;
import com.digitaldesign.murashkina.services.exceptions.team.InvalidTeamRoleException;
import com.digitaldesign.murashkina.services.exceptions.team.MemberNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.team.TeamIsNullException;
import com.digitaldesign.murashkina.services.mapping.TeamMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.digitaldesign.murashkina.app.services.unit.TestEntitiesCreator.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TeamServiceTest {
    @Spy
    EmployeeRepository employeeRepository;
    @Spy
    ProjectRepository projectRepository;
    @Spy
    TeamRepository teamRepository;
    @Mock
    TeamMapper teamMapper;
    @InjectMocks
    TeamService teamService;

    @Test
    public void createMember() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(true);
        when(teamRepository.existsById(team.getTeamId())).thenReturn(false);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        when(teamRepository.save(any())).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(testTeamDto);
        TeamDto acctualresponse = teamService.createMember(testTeamDto);
        verify(teamRepository, times(1)).save(any(Team.class));
        Assertions.assertEquals(team.getTeamId().getMember().getId(), acctualresponse.getMember());
        Assertions.assertEquals(team.getTeamId().getProject().getProjectId(), acctualresponse.getProject());
        Assertions.assertEquals(team.getRole().name(), acctualresponse.getRole());
    }

    @Test
    public void createMember_teamIsNull() {
        Assertions.assertThrows(TeamIsNullException.class, () -> teamService.createMember(TeamDto.builder().build()));
    }

    @Test
    public void createMember_employeeNotFound() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        when(employeeRepository.existsById(employee.getId())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> teamService.createMember(testTeamDto));
    }

    @Test
    public void createMember_projectNotFound() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(false);
        Assertions.assertThrows(ProjectNotFoundException.class, () -> teamService.createMember(testTeamDto));
    }

    @Test
    public void createMember_employeeAlreadyInTeam() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(true);
        when(teamRepository.existsById(any())).thenReturn(true);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        Assertions.assertThrows(EmployeeAlreadyInTeamException.class, () -> teamService.createMember(testTeamDto));
    }

    @Test
    public void createMember_employeeDeleted() {
        Employee employee = createTestEmployee("user1");
        employee.setStatus(EStatus.BLOCKED);
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(true);
        when(teamRepository.existsById(any())).thenReturn(false);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        Assertions.assertThrows(EmployeeDeletedException.class, () -> teamService.createMember(testTeamDto));
    }

    @Test
    public void createMember_invalidTeamRole() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        testTeamDto.setRole("error role");
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(true);
        when(teamRepository.existsById(any())).thenReturn(false);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));
        Assertions.assertThrows(InvalidTeamRoleException.class, () -> teamService.createMember(testTeamDto));
    }

    @Test
    public void deleteMember() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        Team team = createTeamTest(employee, project);
        TeamDto testTeamDto = createTestTeamDto(employee, project, team.getRole());
        DeleteMember deleteMemberRequest = DeleteMember.builder().member(employee.getId())
                .project(project.getProjectId()).build();
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(true);
        when(employeeRepository.findById(deleteMemberRequest.getMember())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(deleteMemberRequest.getProject())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any(TeamId.class))).thenReturn(true);
        when(teamRepository.findById(any(TeamId.class))).thenReturn(Optional.of(team));
        when(teamMapper.toDto(team)).thenReturn(testTeamDto);
        TeamDto acctualresponse = teamService.deleteMember(deleteMemberRequest);
        verify(teamRepository, times(1)).deleteMember(project, employee);
        Assertions.assertEquals(team.getTeamId().getMember().getId(), acctualresponse.getMember());
        Assertions.assertEquals(team.getTeamId().getProject().getProjectId(), acctualresponse.getProject());
        Assertions.assertEquals(team.getRole().name(), acctualresponse.getRole());
    }

    @Test
    public void deleteMember_employeeNotFound() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        DeleteMember deleteMemberRequest = DeleteMember.builder().member(employee.getId())
                .project(project.getProjectId()).build();
        when(employeeRepository.existsById(employee.getId())).thenReturn(false);
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> teamService.deleteMember(deleteMemberRequest));

    }

    @Test
    public void deleteMember_projectNotFound() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        DeleteMember deleteMemberRequest = DeleteMember.builder().member(employee.getId())
                .project(project.getProjectId()).build();
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(false);
        Assertions.assertThrows(ProjectNotFoundException.class, () -> teamService.deleteMember(deleteMemberRequest));
    }

    @Test
    public void deleteMember_memberNotFound() {
        Employee employee = createTestEmployee("user1");
        Project project = createTestProject();
        DeleteMember deleteMemberRequest = DeleteMember.builder().member(employee.getId())
                .project(project.getProjectId()).build();
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(projectRepository.existsById(project.getProjectId())).thenReturn(true);
        when(employeeRepository.findById(deleteMemberRequest.getMember())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(deleteMemberRequest.getProject())).thenReturn(Optional.of(project));
        when(teamRepository.existsById(any(TeamId.class))).thenReturn(false);
        Assertions.assertThrows(MemberNotFoundException.class, () -> teamService.deleteMember(deleteMemberRequest));
    }

    private TeamDto createTestTeamDto(Employee employee, Project project, TeamRole role) {
        return TeamDto.builder()
                .project(project.getProjectId())
                .member(employee.getId()).role(role.name()).build();
    }
}
