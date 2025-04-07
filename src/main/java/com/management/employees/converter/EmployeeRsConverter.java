package com.management.employees.converter;

import com.management.employees.dto.EmployeeRs;
import com.management.employees.pojo.EmployeePojo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeRsConverter {

    EmployeeRsConverter INSTANCE = Mappers.getMapper( EmployeeRsConverter.class );
    List<EmployeeRs> toDTO(List<EmployeePojo> employeePojos);
    EmployeeRs toDTO(EmployeePojo employeePojos);

}
