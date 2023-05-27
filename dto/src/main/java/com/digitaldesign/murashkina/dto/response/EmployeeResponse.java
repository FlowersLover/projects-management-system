package com.digitaldesign.murashkina.dto.response;

import com.digitaldesign.murashkina.dto.enums.EStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private UUID id;
    private String position;
    private String account;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String password;
    private EStatus status;
}
