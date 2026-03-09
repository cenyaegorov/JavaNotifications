package com.example.javanotifications.outbox.infrastructure.persistence.repositories;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.domain.OutboxEventStatus;
import com.example.javanotifications.outbox.infrastructure.persistence.entities.OutboxEventEntity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public interface JpaOutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID>{
	@Query(value = "SELECT * FROM outbox_events WHERE status = :status ORDER BY created_at LIMIT :limit FOR UPDATE SKIP LOCKED", nativeQuery = true)
	public List<OutboxEventEntity> findByStatusWithLockAndLimit(@Param("status") String status, @Param("limit") int limit);
	
	@Query(value = "SELECT * FROM outbox_events WHERE status = :status AND next_update < :instant ORDER BY created_at LIMIT :limit FOR UPDATE SKIP LOCKED", nativeQuery = true)
	public List<OutboxEventEntity> findByStatusByCompareNextUpdateWithLockAndLimit(@Param("status") String status, @Param("instant") Instant instant, @Param("limit") int limit);
}
