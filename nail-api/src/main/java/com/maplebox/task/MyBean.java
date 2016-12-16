package com.maplebox.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBean {
	static Logger log = LoggerFactory.getLogger(MyBean.class);
	
	public void printMessage() throws Exception {
		log.debug("I am called by MethodInvokingJobDetailFactoryBean using SimpleTriggerFactoryBean");
    }
}
