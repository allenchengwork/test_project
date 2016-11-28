package com.maplebox.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class TaskErrorHandler implements ErrorHandler {
	static Logger log = LoggerFactory.getLogger(TaskErrorHandler.class);
	
	@Override
	public void handleError(Throwable t) {
		log.error("", t);
	}
}
