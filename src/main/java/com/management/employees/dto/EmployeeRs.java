package com.management.employees.dto;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
public class EmployeeRs {

    private final int employeeId;
    private final String employeeName;
    private final BigDecimal employeeSalary;
    private final int employeeAge;
    private final String profileImage;
    private final BigDecimal employeeSalaryAnnual;

    public EmployeeRs(int employeeId,
                      String employeeName,
                      BigDecimal employeeSalary,
                      int employeeAge,
                      String profileImage,
                      BigDecimal employeeSalaryAnnual) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeAge = employeeAge;
        this.profileImage = profileImage;
        this.employeeSalaryAnnual = employeeSalaryAnnual;
    }
}
