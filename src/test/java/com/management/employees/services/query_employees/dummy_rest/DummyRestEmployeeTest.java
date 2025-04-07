package com.management.employees.services.query_employees.dummy_rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.management.employees.UtilObject;
import com.management.employees.dto.EmployeeDummyIdRs;
import com.management.employees.dto.EmployeeDummyRs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class DummyRestEmployeeTest {

    @Mock
    private Cache cache;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private DummyRestEmployee dummyRestEmployee;

    @BeforeEach
    void initializedValues(){
        ReflectionTestUtils.setField(dummyRestEmployee,"EMPLOYEE_ALL_URL","TEST_URL_EMPLOYEE");
        ReflectionTestUtils.setField(dummyRestEmployee,"EMPLOYEE_ID_URL","TEST_URL_EMPLOYEE");
    }

    @Test
    void consultEmployees() {
        //GIVEN
        ResponseEntity<EmployeeDummyRs> responseEntity = new ResponseEntity<>(UtilObject.employeeDummyRs(),
                HttpStatus.OK);
        when(restTemplate.exchange(Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.isNull(),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(responseEntity);
        //WHEN
        EmployeeDummyRs employeeDummyRs = dummyRestEmployee.consultEmployees();
        //THEN
        assertNotNull(employeeDummyRs);
        assertSame(responseEntity.getBody(),employeeDummyRs);
    }

    @Test
    void consultEmployeesHttpClientErrorException() throws JsonProcessingException {
        //GIVEN
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,
                "422",UtilObject.employeeDummyRsErrorBytes(), StandardCharsets.UTF_8);
        doThrow(httpClientErrorException).
        when(restTemplate).exchange(Mockito.anyString(),
                        Mockito.any(HttpMethod.class),
                        Mockito.isNull(),
                        Mockito.any(ParameterizedTypeReference.class));
        //WHEN
        Throwable error = assertThrows(UnsupportedOperationException.class,dummyRestEmployee::consultEmployees);
        //THEN
        assertNotNull(error);
        assertNotNull(error.getMessage());
        assertTrue(error.getMessage().contains("CONSULT_EMPLOYEES | ERROR | HTTP_CODE"));
        assertNotNull(error.getCause());
        assertInstanceOf(HttpClientErrorException.class, error.getCause());
    }

    @Test
    void consultEmployeesHttpServerErrorException() throws JsonProcessingException {
        //GIVEN
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY,
                "422",UtilObject.employeeDummyRsErrorBytes(), StandardCharsets.UTF_8);
        doThrow(httpServerErrorException).
                when(restTemplate).exchange(Mockito.anyString(),
                        Mockito.any(HttpMethod.class),
                        Mockito.isNull(),
                        Mockito.any(ParameterizedTypeReference.class));
        //WHEN
        Throwable error = assertThrows(UnsupportedOperationException.class,dummyRestEmployee::consultEmployees);
        //THEN
        assertNotNull(error);
        assertNotNull(error.getMessage());
        assertTrue(error.getMessage().contains("CONSULT_EMPLOYEES | ERROR | HTTP_CODE"));
        assertNotNull(error.getCause());
        assertInstanceOf(HttpServerErrorException.class, error.getCause());
    }

    @Test
    void consultEmployee() {
        //GIVEN
        when(cache.get(Mockito.any(SimpleKey.class),Mockito.any(Class.class))).thenReturn(null);
        when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
        ResponseEntity<EmployeeDummyIdRs> responseEntity = new ResponseEntity<>(UtilObject.employeeDummyIdRs(),
                HttpStatus.OK);
        when(
                restTemplate.exchange(Mockito.anyString(),
                        Mockito.any(HttpMethod.class),
                        Mockito.isNull(),
                        Mockito.any(Class.class))
        ).thenReturn(responseEntity);
        int employeeId = 1;
        //WHEN
        EmployeeDummyIdRs employeeDummyRs = dummyRestEmployee.consultEmployee(employeeId);
        //THEN
        assertNotNull(employeeDummyRs);
        assertSame(responseEntity.getBody(),employeeDummyRs);
    }


    @Test
    void consultEmployeeHttpClientErrorException() throws JsonProcessingException {
        //GIVEN
        when(cache.get(Mockito.any(SimpleKey.class),Mockito.any(Class.class))).thenReturn(null);
        when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
        HttpClientErrorException httpServerErrorException = new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,
                "422",UtilObject.employeeEmployeeDummyIdRsBytes(), StandardCharsets.UTF_8);
        doThrow(httpServerErrorException).
                when(restTemplate).exchange(Mockito.anyString(),
                        Mockito.any(HttpMethod.class),
                        Mockito.isNull(),
                        Mockito.any(Class.class));
        int employeeId = 1;
        //WHEN
        Throwable error = assertThrows(UnsupportedOperationException.class,()->
                dummyRestEmployee.consultEmployee(employeeId));
        //THEN
        assertNotNull(error);
        assertNotNull(error.getMessage());
        assertTrue(error.getMessage().contains("CONSULT_EMPLOYEE_ID | ERROR | HTTP_CODE"));
        assertNotNull(error.getCause());
        assertInstanceOf(HttpClientErrorException.class, error.getCause());
    }


    @Test
    void consultEmployeeHttpServerErrorException() throws JsonProcessingException {
        //GIVEN

        when(cache.get(Mockito.any(SimpleKey.class),Mockito.any(Class.class))).thenReturn(null);
        when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
        HttpServerErrorException httpServerErrorException = new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY,
                "422",UtilObject.employeeEmployeeDummyIdRsBytes(), StandardCharsets.UTF_8);
        doThrow(httpServerErrorException).
                when(restTemplate).exchange(Mockito.anyString(),
                        Mockito.any(HttpMethod.class),
                        Mockito.isNull(),
                        Mockito.any(Class.class));
        int employeeId = 1;
        //WHEN
        Throwable error = assertThrows(UnsupportedOperationException.class,()->
                dummyRestEmployee.consultEmployee(employeeId));
        //THEN
        assertNotNull(error);
        assertNotNull(error.getMessage());
        assertTrue(error.getMessage().contains("CONSULT_EMPLOYEE_ID | ERROR | HTTP_CODE"));
        assertNotNull(error.getCause());
        assertInstanceOf(HttpServerErrorException.class, error.getCause());
    }
}