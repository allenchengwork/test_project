package com.maplebox.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AnotherBean {
	static Logger log = LoggerFactory.getLogger(AnotherBean.class);
	
	public void printAnotherMessage() {
		log.debug("I am called by Quartz jobBean using CronTriggerFactoryBean");
    }
}
