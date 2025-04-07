package com.management.employees.services.query_employees;

import com.management.employees.UtilObject;
import com.management.employees.dto.DataEmployeeDummyRs;
import com.management.employees.dto.EmployeeDummyIdRs;
import com.management.employees.dto.EmployeeDummyRs;
import com.management.employees.exceptions.ConsultEmployeesException;
import com.management.employees.pojo.EmployeePojo;
import com.management.employees.services.query_employees.dummy_rest.DummyRestEmployee;
import com.management.employees.services.salary_employees.CalculateSalaryAnnual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultEmployeesTest {


    @Mock
    private CalculateSalaryAnnual salaryEmployee;
    @Mock
    private DummyRestEmployee dummyRestEmployee;

    @InjectMocks
    private ConsultEmployees consultEmployees;

    @Test
    void consultEmployees() {
        try{
            //GIVEN
            List<DataEmployeeDummyRs> dataEmployeeDummyRs = UtilObject.dataEmployeesDummyRs();
            when(salaryEmployee.calculateSalaryAnnual(Mockito.any(BigDecimal.class))).thenReturn(new BigDecimal("60000.0"));
            EmployeeDummyRs employeeDummyRs = new EmployeeDummyRs("success",dataEmployeeDummyRs,"OK");
            when(dummyRestEmployee.consultEmployees()).thenReturn(employeeDummyRs);
            //WHEN
            List<EmployeePojo> employeePojos = consultEmployees.consultEmployees();
            //THEN
            assertNotNull(employeePojos);
            assertEquals(1,employeePojos.size());
            assertNotNull(employeePojos.get(0));
            assertEquals(5000,employeePojos.get(0).employeeSalary().doubleValue());
            assertEquals(60000.0,employeePojos.get(0).employeeSalaryAnnual().doubleValue());
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void consultEmployeesConsultEmployeesException() {
        //GIVEN
        List<DataEmployeeDummyRs> dataEmployeeDummyRs = null;
        EmployeeDummyRs employeeDummyRs = new EmployeeDummyRs("error",dataEmployeeDummyRs,"error");
        when(dummyRestEmployee.consultEmployees()).thenReturn(employeeDummyRs);
        //WHEN
        Throwable throwable = assertThrows(ConsultEmployeesException.class, consultEmployees::consultEmployees);
        //THEN
        assertNotNull(throwable);
        assertEquals("error",throwable.getMessage());
    }

    @Test
    void consultEmployee() {
        try{
            //GIVEN
            int employeeId = 1;
            DataEmployeeDummyRs dataEmployeeDummyRs = UtilObject.dataEmployeeDummyRs();
            Optional<DataEmployeeDummyRs> dataEmployeeDummyRsOpt = Optional.of(dataEmployeeDummyRs);
            when(salaryEmployee.calculateSalaryAnnual(Mockito.any(BigDecimal.class))).thenReturn(new BigDecimal("60000.0"));
            EmployeeDummyIdRs employeeDummyRs = new EmployeeDummyIdRs("success",dataEmployeeDummyRsOpt,"OK");
            when(dummyRestEmployee.consultEmployee(dataEmployeeDummyRs.getEmployeeId())).thenReturn(employeeDummyRs);
            //WHEN
            Optional<EmployeePojo> employeePojosOpt = consultEmployees.consultEmployee(employeeId);
            //THEN
            assertNotNull(employeePojosOpt);
            assertTrue(employeePojosOpt.isPresent());
            EmployeePojo employeePojo = employeePojosOpt.get();
            assertEquals(5000,employeePojo.employeeSalary().doubleValue());
            assertEquals(60000.0,employeePojo.employeeSalaryAnnual().doubleValue());
        }catch (Exception e){
            fail();
        }
    }


    @Test
    void consultEmployeeQueryNotValid() {
        //GIVEN
        int employeeId = -1;
        //WHEN
        Throwable throwable =  assertThrows(ConsultEmployeesException.class,()->
                consultEmployees.consultEmployee(employeeId));
        //THEN
        assertNotNull(throwable);
        assertEquals("Consulta no permitida",throwable.getMessage());
    }

    @Test
    void consultEmployeeConsultEmployeesException() {
        //GIVEN
        Optional<DataEmployeeDummyRs> dataEmployeeDummyRsOpt = Optional.empty();
        EmployeeDummyIdRs employeeDummyRs = new EmployeeDummyIdRs("error",dataEmployeeDummyRsOpt,"error");
        when(dummyRestEmployee.consultEmployee(1)).thenReturn(employeeDummyRs);
        //WHEN
        Throwable throwable = assertThrows(ConsultEmployeesException.class,()->
                consultEmployees.consultEmployee(1));
        //THEN
        assertNotNull(throwable);
        assertEquals("error",throwable.getMessage());
    }
}