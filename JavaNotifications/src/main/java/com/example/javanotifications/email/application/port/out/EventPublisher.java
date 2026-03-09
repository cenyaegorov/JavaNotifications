package com.example.javanotifications.email.application.port.out;

import com.example.javanotifications.common.domain.Notification;

public interface EventPublisher {
	public void publishNotification(Notification notification);

}
