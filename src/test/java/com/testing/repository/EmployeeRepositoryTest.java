package com.testing.repository;

import com.testing.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest // h2 in-memory database,
class EmployeeRepositoryTest {
    // TDD - test driven development - we write tests and then write metod
    // BDD - behavior driven development :
    //      given - values, objects
    //      when - action, behavior to test
    //      then - verification of the test results
    @Autowired
    private EmployeeRepository employeeRepository;

    // save employee
    @Test
    @DisplayName("Test for employee saving method")
    void givenEmployee_whenSave_thenReturnSavedEmployee() {
        // given
        Employee employee = new Employee(); // id = null and email = null
        employee.setFirstName("John");
        employee.setLastName("Doe");

        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
        assertThat(savedEmployee.getLastName()).isEqualTo("Doe");
        assertThat(savedEmployee.getId()).isEqualTo(1); }
}