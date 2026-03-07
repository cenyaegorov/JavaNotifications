package com.example.javanotifications.email.infrastructure.repositories;

import org.springframework.stereotype.Repository;

import com.example.javanotifications.email.application.port.out.DomainEntityMapper;
import com.example.javanotifications.email.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.email.domain.notification.Notification;
import com.example.javanotifications.email.infrastructure.persistence.entities.NotificationEntity;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
	private final DomainEntityMapper<Notification, NotificationEntity> mapper;
	private final JpaNotificationRepository repository;
	
	public NotificationRepositoryImpl(DomainEntityMapper<Notification, NotificationEntity> mapper, JpaNotificationRepository repository) {
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public void save(Notification notification) {
		repository.save(mapper.toEntity(notification));
	}

}
