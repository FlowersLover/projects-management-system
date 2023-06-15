package com.digitaldesign.murashkina.dto.request.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;

@Getter
@Setter
@Builder
@ComponentScan
public class UpdatePasswordRequest {
    private String password;
    private String confirmPassword;
}
