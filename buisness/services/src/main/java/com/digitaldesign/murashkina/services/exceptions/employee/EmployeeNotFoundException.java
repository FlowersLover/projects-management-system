package com.digitaldesign.murashkina.services.exceptions.employee;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class EmployeeNotFoundException extends RuntimeException {
}

