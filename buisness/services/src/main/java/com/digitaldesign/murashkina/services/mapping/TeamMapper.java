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


    public TeamDto toDto(Team model) {
        return TeamDto.builder()
                .member(model.getTeamId().getMember().getId())
                .project(model.getTeamId().getProject().getProjectId())
                .role(model.getRole().name()).build();
    }
}

