package com.example.javanotifications.common.infrastructure.persistence.mappers;

import org.springframework.stereotype.Component;

import com.example.javanotifications.common.application.port.out.DomainEntityMapper;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.infrastructure.persistence.entities.NotificationEntity;

@Component
public class NotificationMapperImpl implements DomainEntityMapper<Notification, NotificationEntity> {

	@Override
	public NotificationEntity toEntity(Notification notification) {
		if (notification == null) throw new IllegalArgumentException("notification for mapping to entity is null!");
		NotificationEntity entity = new NotificationEntity();
		entity.setRequestId(notification.getId());
		entity.setEmail(notification.getEmail());
		entity.setPayload(notification.getPayload());
		entity.setCreatedAt(notification.getCreatedAt());
		entity.setUpdatedAt(notification.getUpdatedAt());
		entity.setAttemptCount(notification.getAttemptCount());
		entity.setStatus(notification.getStatus());
		entity.setNextUpdate(notification.getNextUpdate());
		return entity;
	}

	@Override
	public Notification toDomain(NotificationEntity entity) {
		if (entity == null) throw new IllegalArgumentException("entity for mapping to notification is null!");
		return new Notification(
				entity.getRequestId(),
				entity.getEmail(),
				entity.getPayload(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getAttemptCount(),
				entity.getStatus(),
				entity.getNextUpdate()
				);
	}

}
