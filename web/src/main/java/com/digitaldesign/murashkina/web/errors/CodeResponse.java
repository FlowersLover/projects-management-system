package com.digitaldesign.murashkina.web.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CodeResponse {
    private String code;
    private String message;
}
