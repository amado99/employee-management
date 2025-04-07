package com.management.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DataEmployeeDummyRs {

    @JsonProperty("id")
    private int employeeId;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_salary")
    private BigDecimal employeeSalary;

    @JsonProperty("employee_age")
    private int employeeAge;

    @JsonProperty("profile_image")
    private String profileImage;

}
