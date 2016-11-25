package com.maplebox.nail.listener;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.maplebox.nail.task.TestTask;

@Component
public class ServerStartEventListener {
	static Logger log = LoggerFactory.getLogger(ServerStartEventListener.class);
	
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
		testTask.taskSchedule("0/10 * * * * MON-FRI");
    }
}
