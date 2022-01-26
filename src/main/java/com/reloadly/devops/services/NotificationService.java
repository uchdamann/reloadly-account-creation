package com.reloadly.devops.services;

import com.reloadly.devops.request.dtos.NotificationDTO;

public interface NotificationService {
	<T extends NotificationDTO> void notifyUser(T notificationRequest);
}
