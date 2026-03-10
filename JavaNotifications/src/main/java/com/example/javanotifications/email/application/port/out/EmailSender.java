package com.example.javanotifications.email.application.port.out;

public interface EmailSender {
	public void send(String email, String text);
}
