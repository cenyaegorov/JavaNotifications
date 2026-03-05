package com.example.javanotifications.email.application.dto;

import java.util.UUID;

public record SendNotificationCommand(
		UUID requestId,
		String email
		){
}
