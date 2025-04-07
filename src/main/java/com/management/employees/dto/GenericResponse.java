package com.management.employees.dto;

import com.management.employees.enums.NotificationEnum;
import lombok.Getter;

import java.util.Map;


@Getter
public class GenericResponse<T> {

    private final T data;
    private final Map<NotificationEnum,String> notifications;

    public GenericResponse(T data, Map<NotificationEnum, String> notifications) {
        this.data = data;
        this.notifications = notifications;
    }

    public GenericResponse(Map<NotificationEnum, String> notifications) {
        this.data = null;
        this.notifications = notifications;
    }
}
