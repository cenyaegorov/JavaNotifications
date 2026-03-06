package com.example.javanotifications.email.application.port.in;

import com.example.javanotifications.email.application.dto.SendNotificationCommand;

public interface SendNotificationUsecasePort {
	public boolean execute(SendNotificationCommand command);
}
