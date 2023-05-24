package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpRequest {
    private String account;
    private String password;

}
