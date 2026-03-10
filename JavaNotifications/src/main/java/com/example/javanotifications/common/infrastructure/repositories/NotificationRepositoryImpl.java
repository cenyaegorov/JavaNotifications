package com.example.javanotifications.common.infrastructure.repositories;

import org.springframework.stereotype.Repository;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.infrastructure.persistence.entities.NotificationEntity;
import com.example.javanotifications.email.application.port.out.DomainEntityMapper;

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
