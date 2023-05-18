package com.digitaldesign.murashkina.dto.request.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;

}
