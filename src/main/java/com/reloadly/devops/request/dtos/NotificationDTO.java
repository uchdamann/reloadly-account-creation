package com.reloadly.devops.request.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.reloadly.devops.constants.NotificationType;

import lombok.Data;

@Data
public class NotificationDTO {
    private Date createdOn = new Date();
    private String username;
    private String firstname;
    @NotNull
    private NotificationType notificationType;
}