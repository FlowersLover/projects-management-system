package com.digitaldesign.murashkina.services.exceptions.task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Сотрудник не является участником команды")
public class EmployeeNotMemberOfTeamException extends RuntimeException {
}