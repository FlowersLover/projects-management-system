package com.digitaldesign.murashkina.models.team;

import com.digitaldesign.murashkina.dto.enums.TeamRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "team")
@AllArgsConstructor
@ToString
public class Team {
    @EmbeddedId
    private TeamId teamId;
    @Enumerated(EnumType.STRING)
    private TeamRole role;

    public Team() {

    }
}
