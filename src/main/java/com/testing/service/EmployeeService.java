package com.testing.service;

import com.testing.entity.Employee;
import com.testing.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        Optional<Employee> savedEmployee =
                employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new RuntimeException("Employee already exists " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteById(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
