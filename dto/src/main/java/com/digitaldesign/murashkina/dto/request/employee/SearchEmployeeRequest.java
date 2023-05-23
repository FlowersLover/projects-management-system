package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchEmployeeRequest {
    private String account;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;

}
