package com.example.javanotifications.email.application.port.out.repositories;

import com.example.javanotifications.email.domain.notification.Notification;

public interface NotificationRepository {
	public void save(Notification notification);
}
