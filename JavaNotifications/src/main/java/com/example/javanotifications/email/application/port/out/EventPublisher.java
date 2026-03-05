package com.example.javanotifications.email.application.port.out;

import com.example.javanotifications.email.domain.notification.Notification;

public interface EventPublisher {
	public void publish(Notification notification);

}
