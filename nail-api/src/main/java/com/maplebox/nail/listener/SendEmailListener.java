package com.maplebox.nail.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.maplebox.nail.event.SendEmailEvent;

@Component
public class SendEmailListener {
	static Logger log = LoggerFactory.getLogger(SendEmailListener.class);
	
	@Async
	@EventListener(condition = "#event.source=='test'")
    public void testEmail(SendEmailEvent event) {
		log.debug("test event = {}", event);
    }
	
	@Async
	@EventListener(condition = "#event.source=='hello'")
    public void helloEmail(SendEmailEvent event) {
		log.debug("hello event = {}", event);
    }
}
