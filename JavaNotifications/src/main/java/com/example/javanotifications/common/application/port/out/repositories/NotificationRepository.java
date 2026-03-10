package com.example.javanotifications.common.application.port.out.repositories;

import java.util.UUID;

import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.common.domain.NotificationStatus;

import jakarta.transaction.Transactional;

public interface NotificationRepository {
	public void save(Notification notification);
	Notification findByIdAndStatus(UUID id, NotificationStatus status);
}
