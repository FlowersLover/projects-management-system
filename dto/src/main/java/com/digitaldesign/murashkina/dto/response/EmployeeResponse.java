package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import lombok.*;

@Getter
@Setter
@Builder
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
