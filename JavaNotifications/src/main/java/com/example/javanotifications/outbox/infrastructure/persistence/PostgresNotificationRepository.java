package com.example.javanotifications.outbox.infrastructure.persistence;

import com.example.javanotifications.email.application.port.out.NotificationRepository;
import com.example.javanotifications.email.domain.notification.Notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresNotificationRepository implements NotificationRepository {

	@Override
	public void save(Notification notification) {
		
		log.info("notification saved: " + notification.toString());
	}

}
