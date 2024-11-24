package com.testing.controller;

import com.testing.entity.Employee;
import com.testing.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }
}
