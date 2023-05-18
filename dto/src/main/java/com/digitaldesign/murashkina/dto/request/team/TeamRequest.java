package com.digitaldesign.murashkina.dto.request.team;

import com.digitaldesign.murashkina.models.team.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamRequest {
    public String id;
    public String project;
    public TeamRole role;
    public String employee;
}
