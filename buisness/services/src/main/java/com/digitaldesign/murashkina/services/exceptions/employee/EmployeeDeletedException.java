package com.digitaldesign.murashkina.services.exceptions.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Сотрудник удален")
public class EmployeeDeletedException extends RuntimeException {
}
