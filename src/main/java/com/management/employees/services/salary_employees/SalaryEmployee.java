package com.management.employees.services.salary_employees;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SalaryEmployee implements CalculateSalaryAnnual{
    @Override
    public BigDecimal calculateSalaryAnnual(BigDecimal baseSalary) {
        return baseSalary.multiply(BigDecimal.valueOf(MONTHS_YEAR));
    }

}
