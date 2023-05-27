package com.digitaldesign.murashkina.dto.request.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class DeleteMember {
    public UUID project;
    public TeamRole role;
    public UUID member;
}
