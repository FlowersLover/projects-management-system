package com.digitaldesign.murashkina.services.exceptions.task;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class EmailNotSentException extends RuntimeException {
}
