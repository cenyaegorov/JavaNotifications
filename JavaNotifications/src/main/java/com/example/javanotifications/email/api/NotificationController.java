package com.example.javanotifications.email.api;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.javanotifications.email.api.dto.SendNotificationRequest;
import com.example.javanotifications.email.api.dto.SendNotificationResponse;
import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.port.in.SendNotificationUsecasePort;

@RestController
@RequestMapping("/notifications/email")
public class NotificationController {

	private final SendNotificationUsecasePort sendNotification;
	
	public NotificationController(SendNotificationUsecasePort sendNotification) {
		this.sendNotification = sendNotification;
	}
	
	@PostMapping
	public SendNotificationResponse sendNotification(@RequestBody SendNotificationRequest request) {
		UUID id = UUID.randomUUID();
		SendNotificationCommand command = new SendNotificationCommand(id, request.email(), request.payload());
		
		sendNotification.execute(command);
		
		return new SendNotificationResponse(id, "ACCEPTED");
	}
}
