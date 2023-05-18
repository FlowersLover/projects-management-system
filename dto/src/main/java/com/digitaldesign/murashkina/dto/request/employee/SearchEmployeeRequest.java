package com.digitaldesign.murashkina.dto.request.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchEmployeeRequest {
    private String account;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;

}
