package com.digitaldesign.murashkina.models.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Employee {
    private UUID id;
    private String position;
    private UUID account;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private EStatus status;


}
