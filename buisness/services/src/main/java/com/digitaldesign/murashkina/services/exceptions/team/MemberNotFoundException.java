package com.digitaldesign.murashkina.services.exceptions.team;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Участник команды не найден")
public class MemberNotFoundException extends RuntimeException {

}
