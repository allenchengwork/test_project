package com.maplebox.service;

import java.util.List;

import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	static Logger log = LoggerFactory.getLogger(EmailService.class);
	
	@Async
	public void sendEmail(List<InternetAddress> fromAddress, List<InternetAddress> toAddress) {
		log.info("**************EmailServiceImpl sendEmail*************");
	}
}