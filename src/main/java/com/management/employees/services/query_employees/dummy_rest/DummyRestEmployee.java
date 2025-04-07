package com.management.employees.services.query_employees.dummy_rest;

import com.management.employees.dto.DataEmployeeDummyRs;
import com.management.employees.dto.EmployeeDummyIdRs;
import com.management.employees.dto.EmployeeDummyRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
public class DummyRestEmployee{

    @Value("${employee-all-dummy-service-v1}")
    private String EMPLOYEE_ALL_URL;
    @Value("${employee-id-dummy-service-v1}")
    private String EMPLOYEE_ID_URL;

    private final CacheManager cacheManager;

    private final RestTemplate restTemplate;

    public DummyRestEmployee(@Qualifier("restTemplateGeneral") RestTemplate restTemplate,
                             CacheManager cacheManager) {
        this.restTemplate = restTemplate;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value="employees-all",unless ="#status == 'success'")
    public EmployeeDummyRs consultEmployees() {
        try {
            RequestEntity<Void> entity = null;
            ResponseEntity<EmployeeDummyRs> response = restTemplate.exchange(EMPLOYEE_ALL_URL,HttpMethod.GET, entity,
                    new ParameterizedTypeReference<>() {});
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            String message = String.format("CONSULT_EMPLOYEES | ERROR | HTTP_CODE %s | RESPONSE %s ",
                    e.getStatusCode(),
                    e.getResponseBodyAsString());
            throw new UnsupportedOperationException(message,e);
        }
    }
    @Cacheable(value="employees-id",unless ="#status == 'success'")
    public EmployeeDummyIdRs consultEmployee(int employeeId){

        Cache cache = cacheManager.getCache("employees-all");
        EmployeeDummyRs cachedEmployee = cache.get(SimpleKey.EMPTY, EmployeeDummyRs.class);
        if(cachedEmployee != null){
            Optional<DataEmployeeDummyRs> dataEmployeeDummyRsOpt = cachedEmployee
                    .getData()
                    .stream()
                    .filter(o->o.getEmployeeId()==employeeId).findAny();
            if(dataEmployeeDummyRsOpt.isPresent()) return
                    new EmployeeDummyIdRs(cachedEmployee.getStatus(),dataEmployeeDummyRsOpt,cachedEmployee.getMessage());
        }


        try{
            RequestEntity<Void> entity = null;
            String url = EMPLOYEE_ID_URL.concat(String.valueOf(employeeId));
            ResponseEntity<EmployeeDummyIdRs> response = restTemplate.exchange(url,HttpMethod.GET, entity,
                    EmployeeDummyIdRs.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e){
            String message = String.format("CONSULT_EMPLOYEE_ID | ERROR | HTTP_CODE %s | RESPONSE %s ",
                    e.getStatusCode(),
                    e.getResponseBodyAsString());
            throw new UnsupportedOperationException(message,e);
        }
    }

}
