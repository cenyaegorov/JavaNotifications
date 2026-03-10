package com.example.javanotifications.email.infrastructure.persistence;

import org.springframework.stereotype.Service;

import com.example.javanotifications.email.application.port.out.EmailSender;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender{

	@Override
	public void send(String email, String text) {
		log.info("Email " + email + " sended with text " + text);
	}
	
}