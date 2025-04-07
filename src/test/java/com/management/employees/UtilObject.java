package com.management.employees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.employees.dto.DataEmployeeDummyRs;
import com.management.employees.dto.EmployeeDummyIdRs;
import com.management.employees.dto.EmployeeDummyRs;
import com.management.employees.pojo.EmployeePojo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UtilObject {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private UtilObject() {
    }

    public static DataEmployeeDummyRs dataEmployeeDummyRs(){
        return new DataEmployeeDummyRs(1,
                "employee test",
                new BigDecimal(5000),
                20,
                "");
    }

    public static List<DataEmployeeDummyRs> dataEmployeesDummyRs(){
        return List.of(dataEmployeeDummyRs());
    }


    public static EmployeeDummyRs employeeDummyRs(){
        return new EmployeeDummyRs("success",dataEmployeesDummyRs(),"ok");
    }

    public static EmployeeDummyRs employeeDummyRsError(){
        return new EmployeeDummyRs("error",null,"error");
    }

    public static byte[] employeeDummyRsErrorBytes() throws JsonProcessingException {
        EmployeeDummyRs employeeDummyRs = employeeDummyRsError();
        return objectMapper.writeValueAsBytes(employeeDummyRs);
    }
    public static EmployeeDummyIdRs employeeDummyIdRs(){
        return new EmployeeDummyIdRs("success", Optional.of(dataEmployeeDummyRs()),"ok");
    }

    public static EmployeeDummyIdRs employeeDummyIdRsError(){
        return new EmployeeDummyIdRs("error", null,"error");
    }

    public static byte[] employeeEmployeeDummyIdRsBytes() throws JsonProcessingException {
        EmployeeDummyIdRs employeeDummyRs = employeeDummyIdRsError();
        return objectMapper.writeValueAsBytes(employeeDummyRs);
    }

    public static List<EmployeePojo> employeePojos(){
        return List.of(employeePojo());
    }
    public static EmployeePojo employeePojo(){
        return new EmployeePojo(dataEmployeeDummyRs(),(x)->x.multiply(new BigDecimal(12)) );
    }

}
