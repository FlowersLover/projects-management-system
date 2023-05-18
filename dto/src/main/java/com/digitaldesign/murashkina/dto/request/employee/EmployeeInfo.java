package com.digitaldesign.murashkina.dto.request.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeInfo {
    private String position;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String password;
}
