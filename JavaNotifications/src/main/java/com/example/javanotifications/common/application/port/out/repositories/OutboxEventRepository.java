package com.example.javanotifications.common.application.port.out.repositories;

import java.time.Instant;
import java.util.List;

import com.example.javanotifications.outbox.domain.OutboxEvent;
import com.example.javanotifications.outbox.domain.OutboxEventStatus;

public interface OutboxEventRepository {
	public void saveEvent(OutboxEvent event);
	public List<OutboxEvent> findByStatusWithLockAndLimit(OutboxEventStatus status, int limit);
	public void saveAll(List<OutboxEvent> events);
	public List<OutboxEvent> findByStatusByCompareNextUpdateWithLockAndLimit(OutboxEventStatus status, Instant instant, int limit);
}
