package com.management.employees.controller;

import com.management.employees.UtilObject;
import com.management.employees.exceptions.ConsultEmployeesException;
import com.management.employees.pojo.EmployeePojo;
import com.management.employees.services.query_employees.IConsultEmployees;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IConsultEmployees consultEmployees;

    @Test
    void getEmployees() {
        try {
            //GIVEN
            HttpHeaders headers = new HttpHeaders();
            headers.add("request-id", UUID.randomUUID().toString());
            List<EmployeePojo> employeePojos = UtilObject.employeePojos();
            when(consultEmployees.consultEmployees()).thenReturn(employeePojos);
            //WHEN
            mockMvc.perform(get("http://localhost:8080/api/v1/employees")
                            .headers(headers))
            //THEN
                    .andExpect(status().isOk());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void getEmployees422() {
        try {
            //GIVEN
            HttpHeaders headers = new HttpHeaders();
            headers.add("request-id", UUID.randomUUID().toString());
            ConsultEmployeesException consultEmployeesException = new ConsultEmployeesException("error");
            doThrow(consultEmployeesException).when(consultEmployees).consultEmployees();
            //WHEN
            mockMvc.perform(get("http://localhost:8080/api/v1/employees")
                            .headers(headers))
                    //THEN
                    .andExpect(status().is4xxClientError());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void getEmployee() {
        try {
            //GIVEN
            HttpHeaders headers = new HttpHeaders();
            headers.add("request-id", UUID.randomUUID().toString());
            EmployeePojo employeePojos = UtilObject.employeePojo();
            when(consultEmployees.consultEmployee(Mockito.anyInt())).thenReturn(Optional.of(employeePojos));
            //WHEN
            mockMvc.perform(get("http://localhost:8080/api/v1/employees/1").headers(headers))
                    //THEN
                    .andExpect(status().isOk());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void getEmployee422() {
        try {
            //GIVEN
            HttpHeaders headers = new HttpHeaders();
            headers.add("request-id", UUID.randomUUID().toString());
            ConsultEmployeesException consultEmployeesException = new ConsultEmployeesException("error");
            doThrow(consultEmployeesException).when(consultEmployees).consultEmployees();
            //WHEN
            mockMvc.perform(get("http://localhost:8080/api/v1/employees/1")
                            .headers(headers))
                    //THEN
                    .andExpect(status().isOk());
        } catch (Exception e){
            fail();
        }
    }
}