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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
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
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

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
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when
        // then
        assertThrows(RuntimeException.class,
                () -> employeeService.save(employee));

        verify(employeeRepository, never()).save(employee);
    }

    @Test
    @DisplayName("Finding all employees - positive scenario")
    void givenEmployee_whenFindAllEmployees_thenReturnEmployees() {
        // given
        Employee employee1 = new Employee("Joe", "Mike", "adam@mike.com");
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when
        List<Employee> employeeList = employeeService.findAll();

        // that
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
        assertThat(employeeList).hasSize(2);
    }

    @Test
    @DisplayName("Finding all employees - negative scenario")
    void givenEmployee_whenFindAllEmployees_thenReturnEmptyList() {
        // given
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Employee> employeeList = employeeService.findAll();

        // that
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
    }

    @Test
    @DisplayName("Finding employee by his id positive scenario")
    void givenEmployee_whenFindById_thenReturnEmployee() {
        // given
        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(anyLong());

        // then
        assertThat(optionalEmployee).isNotNull();
        assertThat(optionalEmployee).isEqualTo(Optional.of(employee));
    }

    @Test
    @DisplayName("Finding employee by his id negative scenario")
    void givenEmployee_whenFindById_thenOptionalOfEmpty() {
        // given
        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(anyLong());

        // then
        assertThat(optionalEmployee).isNotNull();
        assertThat(optionalEmployee).isEmpty();
    }

    @Test
    @DisplayName("Updating an employee")
    void givenEmployee_whenUpdateEmployee_thenReturnEmployee() {
        // given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("123@123");
        employee.setFirstName("Test");

        // when
        Employee updatedEmployee = employeeService.update(employee);

        // then
        assertThat(updatedEmployee.getEmail()).isEqualTo("123@123");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Test");
    }

    @Test
    @DisplayName("Deleting an employee by his id")
    void givenEmployee_whenDeleteById_thenVerifyUsedOnce() {
        // given
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when
        employeeService.deleteById(employeeId);

        // then
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}