package com.management.employees.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class EmployeeDummyRs {

    private String status;
    private List<DataEmployeeDummyRs> data;
    private String message;


}
