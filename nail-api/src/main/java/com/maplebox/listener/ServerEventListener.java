package com.maplebox.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.maplebox.task.TestTask;

@Component
public class ServerEventListener {
	static Logger log = LoggerFactory.getLogger(ServerEventListener.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@EventListener
    public void contextRefreshedEvent(ContextRefreshedEvent event) {
		log.trace("=====TRACE=====");
		log.debug("=====DEBUG=====");
		log.info("=====INFO=====");
		log.warn("=====WARN=====");
		log.error("=====ERROR=====");

		TestTask testTask = applicationContext.getBean(TestTask.class);
		//testTask.taskSchedule("0/10 * * * * MON-FRI");
    }
	
	@EventListener
    public void contextStoppedEvent(ContextStoppedEvent event) {
		log.debug("contextStoppedEvent");
	}
}
