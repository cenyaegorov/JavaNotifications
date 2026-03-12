package com.example.javanotifications.email.api;

import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.javanotifications.common.application.port.out.repositories.NotificationRepository;
import com.example.javanotifications.common.domain.Notification;
import com.example.javanotifications.email.api.dto.NotificationStatusResponse;
import com.example.javanotifications.email.api.dto.SendNotificationRequest;
import com.example.javanotifications.email.api.dto.SendNotificationResponse;
import com.example.javanotifications.email.application.dto.SendNotificationCommand;
import com.example.javanotifications.email.application.port.in.SendEmailNotificationUseCasePort;

@RestController
@RequestMapping("/notifications/email")
public class NotificationController {

	private final SendEmailNotificationUseCasePort sendNotification;
	private final NotificationRepository repository;
	
	public NotificationController(SendEmailNotificationUseCasePort sendNotification, NotificationRepository repositroy, NotificationRepository repository) {
		this.sendNotification = sendNotification;
		this.repository = repository;
		
	}
	
	@PostMapping
	public SendNotificationResponse sendNotification(@RequestBody SendNotificationRequest request) {
		UUID id = UUID.randomUUID();
		SendNotificationCommand command = new SendNotificationCommand(id, request.email(), request.payload());
		
		sendNotification.execute(command);
		
		return new SendNotificationResponse(id, "ACCEPTED");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotificationStatusResponse> getStatus(@PathVariable UUID id) throws NotFoundException {
		Notification notification = repository.findById(id);
		
		if (notification == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		return ResponseEntity.ok(new NotificationStatusResponse(id, notification.getStatus().name(), notification.getAttemptCount()));
	}
}
