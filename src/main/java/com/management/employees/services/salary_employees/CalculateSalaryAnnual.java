package com.management.employees.services.salary_employees;

import java.math.BigDecimal;

public interface CalculateSalaryAnnual {
    byte MONTHS_YEAR = 12;
    BigDecimal calculateSalaryAnnual(BigDecimal baseSalary);

}
