package com.digitaldesign.murashkina.models.employee;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String position;
    private String account;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private EStatus status;
    private String password;

}
