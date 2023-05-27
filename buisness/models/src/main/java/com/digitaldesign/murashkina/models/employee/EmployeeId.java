package com.digitaldesign.murashkina.models.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

@Data
@AllArgsConstructor
@ComponentScan
public  class EmployeeId {
    private UUID id;

    public UUID getId() {
        return id;
    }
}
