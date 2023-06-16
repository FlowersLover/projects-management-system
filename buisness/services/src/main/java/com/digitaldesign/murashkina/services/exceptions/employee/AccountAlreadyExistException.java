package com.digitaldesign.murashkina.services.exceptions.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Пользователь с учетной записью уже существует")
public class AccountAlreadyExistException extends RuntimeException {
}
