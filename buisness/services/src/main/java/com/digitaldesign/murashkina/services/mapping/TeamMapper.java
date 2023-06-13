package com.digitaldesign.murashkina.services.mapping;

import com.digitaldesign.murashkina.dto.request.team.TeamDto;
import com.digitaldesign.murashkina.models.team.Team;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TeamMapper {
    private final ModelMapper modelMapper;

    public TeamMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Team toEntity(TeamDto request) {
        return this.modelMapper.map(request, Team.class);
    }

    public TeamDto toDto(Team model) {
        TeamDto dto = TeamDto.builder()
                .member(model.getTeamId().getMember().getId())
                .project(model.getTeamId().getProject().getProjectId())
                .role(model.getRole().name()).build();
        return dto;
    }
}

