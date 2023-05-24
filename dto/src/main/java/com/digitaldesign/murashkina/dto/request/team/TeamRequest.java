package com.digitaldesign.murashkina.dto.request.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequest {
    public String id;
    public String project;
    public TeamRole role;
    public String member;
}
