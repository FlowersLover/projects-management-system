package com.digitaldesign.murashkina.services.exceptions.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Задача имеет значание null")
public class TaskIsNullException extends RuntimeException {
}
