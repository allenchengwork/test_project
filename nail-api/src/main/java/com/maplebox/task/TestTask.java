package com.maplebox.task;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
	static Logger log = LoggerFactory.getLogger(TestTask.class);
	
	@Autowired
	private TaskScheduler taskScheduler;
	
	private static int index = 0;
	
	private ScheduledFuture<?> scheduledFuture;
	
	@Scheduled(cron = "0 0/1 * * * MON-FRI")
	public void doSomething() throws Exception {
		log.debug("doSomething");
	}
	
	@Scheduled(fixedRateString = "${task.rate}")
	public void doTest() {
		log.debug("doTest");
	}
	
	public synchronized void taskSchedule(String cron) {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(true);
		}
		scheduledFuture = taskScheduler.schedule(new Runnable() {
			private int number = 0;
			private int count = 0;
			
			@Override
			public void run() {
				if (count == 0) {
					number = index;
					index++;
				}
				log.debug("*****taskSchedule*****>>>"+number+"<<<"+count);
				count++;
			}
		}, new CronTrigger(cron));
	}
}
