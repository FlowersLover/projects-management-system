package com.digitaldesign.murashkina.dto.request.employee;

import com.digitaldesign.murashkina.models.team.TeamRole;
import lombok.Data;

import java.util.UUID;

@Data
public class SearchEmployeeFilter {
    private String position;
    private String lastName;
    private String firstName;
    private String middleName;
    private String account;
    private String email;
    private String password;
    private UUID member;
    private String role;
    private UUID project;
}
