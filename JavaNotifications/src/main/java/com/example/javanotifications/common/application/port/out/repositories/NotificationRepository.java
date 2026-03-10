package com.example.javanotifications.common.application.port.out.repositories;

import java.util.UUID;

import com.example.javanotifications.common.domain.Notification;

import jakarta.transaction.Transactional;

public interface NotificationRepository {
	public void save(Notification notification);
	@Transactional
	public Notification findById(UUID id);
}
