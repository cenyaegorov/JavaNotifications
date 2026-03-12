package com.example.javanotifications.email.application.port.in;

import com.example.javanotifications.outbox.dto.NotificationPayload;

public interface ProcessEmailNotificationUseCasePort {
	public void execute(NotificationPayload payload);
}
