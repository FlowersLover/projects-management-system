package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeEmployeeInfo {
    private String position;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String password;
}
