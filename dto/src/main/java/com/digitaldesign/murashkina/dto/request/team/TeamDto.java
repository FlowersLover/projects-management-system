package com.digitaldesign.murashkina.dto.request.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private UUID project;
    private TeamRole role;
    private UUID member;
}
