package com.example.javanotifications.common.infrastructure.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

	@Override
	@Transactional
	public List<Notification> findByStatusAndCompareToNextUpdateLimitWithLock(NotificationStatus status,
			Instant instant, int limit) {
		List<NotificationEntity> entities = repository.findByStatusAndCompareToNextUpdateLimitWithLock(status, instant, limit);
		
		
		return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public void saveAll(List<Notification> notifications) {
		repository.saveAll(notifications.stream().map(mapper::toEntity).collect(Collectors.toList()));
		
	}

}
