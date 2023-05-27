package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

@Getter
@Setter
@Builder
@ComponentScan
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
