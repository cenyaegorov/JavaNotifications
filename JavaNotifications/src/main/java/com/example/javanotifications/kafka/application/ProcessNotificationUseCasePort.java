package com.example.javanotifications.kafka.application;

import com.example.javanotifications.outbox.dto.NotificationPayload;

public interface ProcessNotificationUseCasePort {
	public void execute(NotificationPayload payload);
}
