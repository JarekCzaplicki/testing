package com.testing.service;

import com.testing.entity.Employee;
import com.testing.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    //Test Employee 'save employee' - happy path - test passes; test with exception
    // List<Employee> findAll() - two test path
    // Optional<Employee> findById(Long id) - two test path
    // Employee update(Employee employee)
    // void deleteBy(Long id)

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(
                "Adam"
                , "Tadeusz"
                , "adam@tadeusz.com"
        );
    }

    @Test
    @DisplayName("Saving employee without any exceptions")
    void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
        // given
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = employeeService.save(employee);

        //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    @DisplayName("Saving employee with an exception")
    void givenEmployee_whenSaveEmployee_thenThrowException() {
        // given
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when
        // then
        assertThrows(RuntimeException.class,
                () -> employeeService.save(employee));
        verify(employeeRepository, never()).save(employee);
    }
}