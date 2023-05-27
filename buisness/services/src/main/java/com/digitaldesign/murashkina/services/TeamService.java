package com.digitaldesign.murashkina.services;

import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.models.team.Team;
import com.digitaldesign.murashkina.models.team.TeamId;
import com.digitaldesign.murashkina.repositories.EmployeeRepository;
import com.digitaldesign.murashkina.repositories.ProjectRepository;
import com.digitaldesign.murashkina.repositories.TeamRepository;
import com.digitaldesign.murashkina.services.mapping.TeamMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Team team = Team.builder()
                .role(teamRequest.getRole())
                .teamId(TeamId.builder()
                        .member(employeeRepository.findById(teamRequest.getMember()).get())
                        .project(projectRepository.findById(teamRequest.getProject()).get())
                        .build())
                .build();
        teamRepository.save(team);
        return teamMapper.toDto(team);
    }

    public TeamDto deleteMember(TeamDto teamRequest) {
        Team team = Team.builder()
                .role(teamRequest.getRole())
                .teamId(TeamId.builder()
                        .member(employeeRepository.findById(teamRequest.getMember()).get())
                        .project(projectRepository.findById(teamRequest.getProject()).get())
                        .build())
                .build();
        teamRepository.deleteMember(team.getTeamId().getProject().getId(), team.getTeamId().getMember().getId());
        return teamMapper.toDto(team);
    }

    public List<TeamDto> getAll() {
        return teamRepository.findAll().stream().map(team -> teamMapper.toDto(team)).collect(Collectors.toList());
    }
}
