package com.digitaldesign.murashkina.services.exceptions.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Задача не найдена")
public class TaskNotFoundException extends RuntimeException {
}
