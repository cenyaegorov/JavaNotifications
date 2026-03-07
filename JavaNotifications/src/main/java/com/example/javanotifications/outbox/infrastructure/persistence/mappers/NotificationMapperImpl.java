package com.example.javanotifications.outbox.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.javanotifications.email.application.port.out.NotificationMapper;
import com.example.javanotifications.email.domain.notification.Notification;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.NotificationEntity;

@Component
public class NotificationMapperImpl implements NotificationMapper {

	@Override
	public Notification toNotification(NotificationEntity entity) {
		return new Notification(
				entity.getRequestId(),
				entity.getEmail(),
				entity.getPayload(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getAttemptCount(),
				entity.getStatus()
				);
	}

	@Override
	public NotificationEntity toEntity(Notification notification) {
		NotificationEntity entity = new NotificationEntity();
		entity.setRequestId(notification.getId());
		entity.setEmail(notification.getEmail());
		entity.setPayload(notification.getPayload());
		entity.setCreatedAt(notification.getCreatedAt());
		entity.setUpdatedAt(notification.getUpdatedAt());
		entity.setAttemptCount(notification.getAttemptCount());
		entity.setStatus(notification.getStatus());
		return entity;
	}

}
