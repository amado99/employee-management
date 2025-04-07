package com.management.employees.services.salary_employees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SalaryEmployeeTest {

    @InjectMocks
    private SalaryEmployee salaryEmployee;

    @Test
    void calculateSalaryAnnual() {
        //GIVEN
        BigDecimal baseSalary = new BigDecimal(5000);
        //WHEN
        BigDecimal annualSalary = salaryEmployee.calculateSalaryAnnual(baseSalary);
        //THEN
        assertNotNull(annualSalary);
        assertEquals(60000.0,annualSalary.doubleValue());
    }
}