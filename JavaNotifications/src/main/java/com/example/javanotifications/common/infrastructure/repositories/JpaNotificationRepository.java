package com.example.javanotifications.common.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.javanotifications.common.domain.NotificationStatus;
import com.example.javanotifications.common.infrastructure.persistence.entities.NotificationEntity;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, UUID>{
	public Optional<NotificationEntity> findByIdAndStatus(UUID id, NotificationStatus status);
}
