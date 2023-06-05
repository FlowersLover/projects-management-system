package com.digitaldesign.murashkina.services.exceptions.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessToResourceDeniedException extends RuntimeException {
}
