package com.testing.repository;

import com.testing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Dictionary;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);// query made from function name by Spring Data JPA

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT e FROM Employee as e WHERE e.firstName = ?1 AND e.lastName = ?2")
    Optional<Employee> findByFirstNameAndLastNameJPQL(String firstName, String lastName);

//    @Query(value = "SELECT * FROM employees as e WHERE e.first_name = ?1 AND e.last_name = ?2", nativeQuery = true)
//    Optional<Employee> findByFirstNameAndLastNameNativeSQL(String firstName, String lastName);


//    @Query(value = "SELECT * FROM employees as e WHERE e.first_name = :firstName AND e.last_name = :lastName", nativeQuery = true)
//    Optional<Employee> findByFirstNameAndLastNameNativeSQL(String firstName, String lastName);

    @Query(value = "SELECT * FROM employees as e WHERE e.first_name = :firstName AND e.last_name = :lastName", nativeQuery = true)
    Optional<Employee> findByFirstNameAndLastNameNativeSQL(@Param("firstName")String asdasdadad, String lastName);
}
