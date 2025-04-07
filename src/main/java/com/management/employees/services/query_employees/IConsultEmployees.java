package com.management.employees.services.query_employees;

import com.management.employees.exceptions.ConsultEmployeesException;
import com.management.employees.pojo.EmployeePojo;

import java.util.List;
import java.util.Optional;

public interface IConsultEmployees {

    List<EmployeePojo> consultEmployees() throws ConsultEmployeesException;
    Optional<EmployeePojo> consultEmployee(int employeeId) throws ConsultEmployeesException;

}
