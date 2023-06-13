package com.digitaldesign.murashkina.services;

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
import com.digitaldesign.murashkina.services.exceptions.employee.EmployeeNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.project.ProjectNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.team.EmployeeAlreadyInTeamException;
import com.digitaldesign.murashkina.services.exceptions.team.InvalidTeamRoleException;
import com.digitaldesign.murashkina.services.exceptions.team.MemberNotFoundException;
import com.digitaldesign.murashkina.services.exceptions.team.TeamIsNullException;
import com.digitaldesign.murashkina.services.mapping.TeamMapper;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.teamMapper = teamMapper;
    }

    public TeamDto createMember(TeamDto teamRequest) {
        teamIsNull(teamRequest);
        employeeNotFound(teamRequest.getMember());
        projectNotFound(teamRequest.getProject());
        employeeAlreadyInTeam(teamRequest);
        EmployeeService.employeeDeleted(employeeRepository.findById(teamRequest.getMember()).get());
        invalidTeamRole(teamRequest.getRole());
        Optional<Employee> member = employeeRepository.findById(teamRequest.getMember());
        Optional<Project> project = projectRepository.findById(teamRequest.getProject());
        TeamId teamId = TeamId.builder()
                .member(member.get())
                .project(project.get()).build();
        Team team = Team.builder()
                .role(TeamRole.valueOf(teamRequest.getRole()))
                .teamId(teamId)
                .build();
        Team savedMember = teamRepository.save(team);
        return teamMapper.toDto(savedMember);
    }

    @Transactional
    public TeamDto deleteMember(DeleteMember teamRequest) {
        employeeNotFound(teamRequest.getMember());
        projectNotFound(teamRequest.getProject());
        memberNotFound(teamRequest);
        Optional<Employee> member = employeeRepository.findById(teamRequest.getMember());
        Optional<Project> project = projectRepository.findById(teamRequest.getProject());
        TeamId teamId = TeamId.builder()
                .member(member.get())
                .project(project.get())
                .build();
        Team team = teamRepository.findById(teamId).get();
        teamRepository.deleteMember(team.getTeamId().getProject(), team.getTeamId().getMember());
        return teamMapper.toDto(team);
    }

    public List<TeamDto> getAll() {
        return teamRepository.findAll().stream().map(team -> teamMapper.toDto(team)).collect(Collectors.toList());
    }

    private void teamIsNull(TeamDto request) {
        if (request == null
                || request.getProject() == null
                || request.getMember() == null
                || request.getRole() == null) {
            throw new TeamIsNullException();
        }
    }

    private void invalidTeamRole(String role) {
        if (!EnumUtils.isValidEnum(TeamRole.class, role)) {
            throw new InvalidTeamRoleException();
        }
    }

    private void employeeNotFound(UUID memberId) {
        if (!employeeRepository.existsById(memberId)) {
            throw new EmployeeNotFoundException();
        }
    }

    private void projectNotFound(UUID projextId) {
        if (!projectRepository.existsById(projextId)) {
            throw new ProjectNotFoundException();
        }
    }

    private void employeeAlreadyInTeam(TeamDto teamRequest) {
        if (teamRepository.existsById(TeamId.builder()
                .project(projectRepository.findById(teamRequest.getProject()).get())
                .member(employeeRepository.findById(teamRequest.getMember()).get()).build())) {
            throw new EmployeeAlreadyInTeamException();
        }
    }

    private void memberNotFound(DeleteMember teamRequest) {
        if (!teamRepository.existsById(TeamId.builder()
                .project(projectRepository.findById(teamRequest.getProject()).get())
                .member(employeeRepository.findById(teamRequest.getMember()).get()).build())) {
            throw new MemberNotFoundException();
        }
    }
}
