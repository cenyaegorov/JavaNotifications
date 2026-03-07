package com.example.javanotifications.outbox.infrastructure.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.example.javanotifications.email.application.port.out.NotificationMapper;
import com.example.javanotifications.email.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.email.domain.notification.Notification;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
	private final NotificationMapper mapper;
	private final JpaNotificationRepository repository;
	
	public NotificationRepositoryImpl(NotificationMapper mapper, JpaNotificationRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public void save(Notification notification) {
		repository.save(mapper.toEntity(notification));
	}

}
