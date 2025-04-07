package com.management.employees.pojo;

import com.management.employees.dto.DataEmployeeDummyRs;
import com.management.employees.dto.EmployeeDummyRs;
import com.management.employees.services.salary_employees.CalculateSalaryAnnual;
import lombok.NonNull;

import java.math.BigDecimal;

public record EmployeePojo( int employeeId,
                            @NonNull String employeeName,
                            @NonNull BigDecimal employeeSalary,
                            int employeeAge,
                            @NonNull String profileImage,
                            @NonNull BigDecimal employeeSalaryAnnual) {

    public EmployeePojo(DataEmployeeDummyRs employeeDummyRs, CalculateSalaryAnnual calculateSalaryAnnual){
        this(employeeDummyRs.getEmployeeId(),
                employeeDummyRs.getEmployeeName(),
                employeeDummyRs.getEmployeeSalary() == null ? new BigDecimal(0) : employeeDummyRs.getEmployeeSalary(),
                employeeDummyRs.getEmployeeAge(),
                employeeDummyRs.getProfileImage(),
                calculateSalaryAnnual.calculateSalaryAnnual(
                        employeeDummyRs.getEmployeeSalary() == null ? new BigDecimal(0) : employeeDummyRs.getEmployeeSalary()
                ));
    }
}
