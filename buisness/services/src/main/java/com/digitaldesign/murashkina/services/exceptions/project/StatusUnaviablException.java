package com.digitaldesign.murashkina.services.exceptions.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Статус недоступен")
public class StatusUnaviablException extends RuntimeException {
}
