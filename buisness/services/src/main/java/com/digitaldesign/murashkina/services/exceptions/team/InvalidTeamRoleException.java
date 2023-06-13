package com.digitaldesign.murashkina.services.exceptions.team;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidTeamRoleException extends RuntimeException {
}
