package com.management.employees.handlers;

import com.management.employees.dto.GenericResponse;
import com.management.employees.enums.NotificationEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class EmployeeControllerHandler {

    @ExceptionHandler
    public ResponseEntity<GenericResponse<Void>> handleStudentNotFoundException(Throwable exception,
                                                                                HttpServletRequest request) {
        String requestId = request.getHeader("request-id");
        log.info("request-id : {} | finally request for employee - system error {}  ",  requestId, exception.getMessage());
        Map<NotificationEnum, String> notifications = new HashMap<>();
        notifications.put(NotificationEnum.ERROR_SYSTEM, "Error del sistema ");
        GenericResponse<Void> genericResponse = new GenericResponse<>(notifications);
        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
