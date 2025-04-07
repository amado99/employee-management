package com.management.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class EmployeeDummyIdRs {

    private String status;
    private Optional<DataEmployeeDummyRs> data;
    private String message;


}
