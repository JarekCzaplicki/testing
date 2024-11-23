package com.testing.repository;

import com.testing.entity.Employee;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private EntityManager entityManager;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        entityManager.createNativeQuery(
                "ALTER TABLE employees ALTER COLUMN id RESTART WITH 1"
        ).executeUpdate();

        employee = new Employee(); // id = null and email = null
        employee.setFirstName("John");
        employee.setLastName("Doe");
    }

    // save employee
    @Test
    @DisplayName("Test for employee saving method")
    void givenEmployee_whenSave_thenReturnSavedEmployee() {
        // given
        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getFirstName()).isEqualTo("John");
        assertThat(savedEmployee.getLastName()).isEqualTo("Doe");
        assertThat(savedEmployee.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Returning non empty list of employees")
    void givenTwoEmployees_whenFindAll_thenListOfEmployeesIsNotEmpty() {
        // given
        Employee employee2 = new Employee(); // id = null and email = null
        employee2.setFirstName("John2");
        employee2.setLastName("Doe2");
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when
        List<Employee> employeeList = employeeRepository.findAll();

        // then
        assertThat(employeeList).isNotNull();
    }

    @Test
    @DisplayName("Returning employees")
    void givenTwoEmployees_whenFindAll_thenReturnListOfEmployees() {
        // given
        Employee employee2 = new Employee(); // id = null and email = null
        employee2.setFirstName("John2");
        employee2.setLastName("Doe2");
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when
        List<Employee> employeeList = employeeRepository.findAll();

        // then
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Find a employee by his id number")
    void givenEmployee_whenFindEmployeeById_thenReturnEmployee() {
        // given
        employeeRepository.save(employee);

        // when
        Optional<Employee> optionalEmployee = employeeRepository.findById(1L);

        //then
        assertThat(optionalEmployee.isPresent()).isTrue();
        assertThat(optionalEmployee.get()).isEqualTo(employee);
    }
}