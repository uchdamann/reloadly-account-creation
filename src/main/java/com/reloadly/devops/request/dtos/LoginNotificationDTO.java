package com.reloadly.devops.request.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginNotificationDTO extends NotificationDTO {
	private String location;
}