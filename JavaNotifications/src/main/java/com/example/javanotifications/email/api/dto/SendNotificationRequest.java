package com.example.javanotifications.email.api.dto;

public record SendNotificationRequest(
		String email,
		String payload
		) {

}
