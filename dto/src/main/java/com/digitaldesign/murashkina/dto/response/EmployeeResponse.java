package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.models.employee.EStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
public class EmployeeResponse {
    private String id;
    private String position;
    private String account;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private EStatus status;
    private String password;
}
