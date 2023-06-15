package com.digitaldesign.murashkina.services.mapping;

import com.digitaldesign.murashkina.dto.response.EmployeeResponse;
import com.digitaldesign.murashkina.dto.request.employee.EmployeeRequest;
import com.digitaldesign.murashkina.dto.request.employee.UpdateEmployeeRequest;
import com.digitaldesign.murashkina.models.employee.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {
    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Employee toEntity(EmployeeRequest request) {
        Employee employee = this.modelMapper.map(request,Employee.class);
        return employee;
    }
    public Employee toEntity(UpdateEmployeeRequest request) {
        Employee employee = this.modelMapper.map(request,Employee.class);
        return employee;
    }
    public EmployeeResponse toDto(Employee model) {
        EmployeeResponse dto = this.modelMapper.map(model, EmployeeResponse.class);
        return dto;
    }


}
