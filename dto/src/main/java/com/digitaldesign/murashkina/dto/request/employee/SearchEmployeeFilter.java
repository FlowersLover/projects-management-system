package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SearchEmployeeFilter {
    private String lastName;
    private String firstName;
    private String middleName;
    private String account;
    private String email;
    private UUID member;
    private String role;
    private UUID project;
}
