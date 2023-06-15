package com.digitaldesign.murashkina.services.exceptions.team;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Сотрудник уже есть в команде")
public class EmployeeAlreadyInTeamException extends RuntimeException {
}
