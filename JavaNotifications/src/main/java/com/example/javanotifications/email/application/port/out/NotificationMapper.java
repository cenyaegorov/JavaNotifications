package com.example.javanotifications.email.application.port.out;

import com.example.javanotifications.email.domain.notification.Notification;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.NotificationEntity;

public interface NotificationMapper {
	public Notification toNotification(NotificationEntity entity);
	public NotificationEntity toEntity(Notification notification);
}
