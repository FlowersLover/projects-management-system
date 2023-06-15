package com.digitaldesign.murashkina.services.exceptions.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Проект не найден")
public class ProjectNotFoundException extends RuntimeException {
}
