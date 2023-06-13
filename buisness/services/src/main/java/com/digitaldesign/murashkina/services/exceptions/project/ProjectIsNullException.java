package com.digitaldesign.murashkina.services.exceptions.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Project is Null")
public class ProjectIsNullException extends RuntimeException {
    public ProjectIsNullException(String projectIsNull) {
    }
}
