package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@Getter
@Setter
@Builder
@ComponentScan
public class EmployeeRequest {
    private String position;
    private String lastName;
    private String firstName;
    private String middleName;
    private String account;
    private String email;
    private String password;
}
