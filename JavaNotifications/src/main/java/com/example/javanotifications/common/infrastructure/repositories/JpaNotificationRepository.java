package com.example.javanotifications.common.infrastructure.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.javanotifications.common.domain.NotificationStatus;
import com.example.javanotifications.common.infrastructure.persistence.entities.NotificationEntity;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, UUID>{
	public Optional<NotificationEntity> findByIdAndStatus(UUID id, NotificationStatus status);
	
	@Query(value = "SELECT * FROM email_requests WHERE status = :status AND next_update < :instant LIMIT :limit FOR UPDATE SKIP LOCKED", nativeQuery = true)
	public List<NotificationEntity> findByStatusAndCompareToNextUpdateLimitWithLock(@Param("status") NotificationStatus status, @Param("instant") Instant instant, @Param("limit") int limit);
}
