package com.example.javanotifications.email.application.port.out.repositories;

import com.example.javanotifications.common.domain.Notification;

public interface NotificationRepository {
	public void save(Notification notification);
}
