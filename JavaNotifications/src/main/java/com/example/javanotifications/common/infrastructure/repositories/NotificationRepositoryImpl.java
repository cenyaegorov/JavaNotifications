package com.example.javanotifications.common.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.javanotifications.common.application.port.out.DomainEntityMapper;
import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.domain.NotificationStatus;
import com.example.javanotifications.common.infrastructure.persistence.entities.NotificationEntity;

import jakarta.transaction.Transactional;

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

	@Override
	@Transactional
	public Notification findByIdAndStatus(UUID id, NotificationStatus status) {
		Optional<NotificationEntity> entity = repository.findByIdAndStatus(id, status);
		if (entity.isEmpty()) return null;
		return mapper.toDomain(entity.get());
	}

}
