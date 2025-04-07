package com.management.employees.controller;

import com.management.employees.converter.EmployeeRsConverter;
import com.management.employees.dto.EmployeeRs;
import com.management.employees.dto.GenericResponse;
import com.management.employees.enums.NotificationEnum;
import com.management.employees.exceptions.ConsultEmployeesException;
import com.management.employees.pojo.EmployeePojo;
import com.management.employees.services.query_employees.IConsultEmployees;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.management.employees.enums.NotificationEnum.ERROR_NEGOCIO;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final IConsultEmployees consultEmployees;

    public EmployeeController(IConsultEmployees consultEmployees) {
        this.consultEmployees = consultEmployees;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<EmployeeRs>>> getEmployees(@RequestHeader("request-id") String requestId){
        try {
            log.info("start request for employees | request-id : {}",requestId);
            List<EmployeePojo> employeePOJO = consultEmployees.consultEmployees();
            List<EmployeeRs> employeeRs = EmployeeRsConverter.INSTANCE.toDTO(employeePOJO);
            GenericResponse<List<EmployeeRs>> response = new GenericResponse<>(employeeRs,null);
            log.info("finally request for employees | request-id : {}",requestId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ConsultEmployeesException c){
            Map<NotificationEnum,String> notifications = new HashMap<>();
            notifications.put(ERROR_NEGOCIO, c.getMessage());
            GenericResponse<List<EmployeeRs>> response = new GenericResponse<>(notifications);
            log.info("finally request for employees - business error {} | request-id : {}",c.getMessage(),requestId);
            return new ResponseEntity<>(response, UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/{employee-id}")
    public ResponseEntity<GenericResponse<EmployeeRs>> getEmployee(@PathVariable("employee-id") int employeeId,
                                                                   @RequestHeader("request-id") String requestId){
        try {
            log.info("start request for employee id | request-id : {}",requestId);
            Optional<EmployeePojo> employeePOJO = consultEmployees.consultEmployee(employeeId);
            EmployeeRs employeeRs = employeePOJO.map(EmployeeRsConverter.INSTANCE::toDTO).orElse(null);
            GenericResponse<EmployeeRs> response = new GenericResponse<>(employeeRs,null);
            log.info("finally request for employee id | request-id : {}",requestId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ConsultEmployeesException | NoSuchElementException c){
            Map<NotificationEnum,String> notifications = new HashMap<>();
            notifications.put(ERROR_NEGOCIO, c.getMessage());
            GenericResponse<EmployeeRs> response = new GenericResponse<>(notifications);
            log.info("finally request for employee id - business error {}  | request-id : {}",c.getMessage(),requestId);
            HttpStatus httpStatus = c instanceof NoSuchElementException ? NOT_FOUND : UNPROCESSABLE_ENTITY;
            return new ResponseEntity<>(response, httpStatus);
        }
    }

}
