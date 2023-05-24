package com.digitaldesign.murashkina.dto.request.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteMember {
    public String project;
    public TeamRole role;
    public String member;
}
