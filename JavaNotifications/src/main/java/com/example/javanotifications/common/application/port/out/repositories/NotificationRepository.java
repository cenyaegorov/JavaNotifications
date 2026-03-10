package com.example.javanotifications.common.application.port.out.repositories;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.domain.NotificationStatus;

import jakarta.transaction.Transactional;

public interface NotificationRepository {
	public void save(Notification notification);
	Notification findByIdAndStatus(UUID id, NotificationStatus status);
	List<Notification> findByStatusAndCompareToNextUpdateLimitWithLock(NotificationStatus status, Instant instant, int limit);
	public void saveAll(List<Notification> notifications);
}
