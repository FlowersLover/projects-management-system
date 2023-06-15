package com.digitaldesign.murashkina.services.exceptions.project;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidProjectStatusException extends RuntimeException {
}
