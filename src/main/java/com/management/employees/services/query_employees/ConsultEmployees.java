package com.management.employees.services.query_employees;

import com.management.employees.dto.DataEmployeeDummyRs;
import com.management.employees.dto.EmployeeDummyIdRs;
import com.management.employees.dto.EmployeeDummyRs;
import com.management.employees.exceptions.ConsultEmployeesException;
import com.management.employees.pojo.EmployeePojo;
import com.management.employees.services.query_employees.dummy_rest.DummyRestEmployee;
import com.management.employees.services.salary_employees.CalculateSalaryAnnual;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultEmployees implements IConsultEmployees{

    private final CalculateSalaryAnnual salaryEmployee;
    private final DummyRestEmployee dummyRestEmployee;

    public ConsultEmployees(@Qualifier("salaryEmployee") CalculateSalaryAnnual salaryEmployee,
                            DummyRestEmployee dummyRestEmployee) {
        this.salaryEmployee = salaryEmployee;
        this.dummyRestEmployee = dummyRestEmployee;
    }

    @Override
    public List<EmployeePojo> consultEmployees() throws ConsultEmployeesException {
        EmployeeDummyRs employeeDummyRs = dummyRestEmployee.consultEmployees();
        validarRespuesta(employeeDummyRs.getStatus(), employeeDummyRs.getMessage());
        List<DataEmployeeDummyRs> employeesDummyRs = employeeDummyRs.getData();
        return employeesDummyRs
                .stream()
                .map(e -> new EmployeePojo(e, salaryEmployee))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeePojo> consultEmployee(int employeeId) throws ConsultEmployeesException {
        if(employeeId<0) throw new ConsultEmployeesException("Consulta no permitida");
        EmployeeDummyIdRs employeeDummyIdRs = dummyRestEmployee.consultEmployee(employeeId);
        validarRespuesta(employeeDummyIdRs.getStatus(), employeeDummyIdRs.getMessage());
        if(employeeDummyIdRs.getData().isEmpty()||employeeDummyIdRs.getData().get().getEmployeeId() == 0)
            throw new NoSuchElementException(String.format("El empleado con el id %s no existe",employeeId));
        return employeeDummyIdRs
                .getData()
                .map(e -> new EmployeePojo(e, salaryEmployee));
    }

    private void validarRespuesta(String responseStatus, String messageResponse) throws ConsultEmployeesException {
        if(!"success".equals(responseStatus)) throw new ConsultEmployeesException(messageResponse);
    }
}
