package com.testing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.entity.Employee;
import com.testing.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Creating new employee")
    void createEmployee() throws Exception {
        // given
        Employee employee = new Employee(
                "Adam"
                ,"Małysz"
                ,"adam@małysz.com"
        );

        given(employeeService.save(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    @DisplayName("Getting all employees")
    void getAllEmployees() throws Exception {
        // given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(new Employee("A", "B", "C"));
        listOfEmployees.add(new Employee("AA", "BZB", "CCC"));
        given(employeeService.findAll()).willReturn(listOfEmployees);

        // when
        ResultActions response = mockMvc.perform(get("/api/employee"));

        // then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())))
                .andExpect(jsonPath("$[0].firstName", is(listOfEmployees.get(0).getFirstName())));
    }

    @Test
    @DisplayName("Getting as employee by his id - positive scenario")
    void getEmployeeById() throws Exception {
        // given
        Employee employee = new Employee("1111", "22222", "121212");
        long employeeId = 1L;
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));

        // when
//        ResultActions response = mockMvc.perform(get("/api/employee/"+employeeId));
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employeeId));


        // then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("Getting as employee by his id - negative scenario")
    void getEmployeeById_negative() throws Exception {
        // given
        given(employeeService.findById(anyLong())).willReturn(Optional.empty());

        // when
//        ResultActions response = mockMvc.perform(get("/api/employee/"+employeeId));
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", 1L));

        // then
        response
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Updating an employee")
    void updateEmployee() throws Exception {
        // given
        Employee employee = new Employee("1111", "22222", "121212");
        given(employeeService.update(employee)).willReturn(employee);

        // when
        ResultActions response = mockMvc.perform(put("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then
        response
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deleting an employee")
    void deleteEmployee() throws Exception {
        // given
        willDoNothing().given(employeeService).deleteById(anyLong());

        // when
        ResultActions response = mockMvc.perform(delete("/api/employee/{id}", 1L));

        // then
        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deleting an employee witch returned information")
    void deleteEmployeewithChec() throws Exception {
        // given
        // delete an employee
        // find an employee by his id
        // for id on witch the employee was deleted it will give my back an 'not found'


        // when


        // then

    }

}