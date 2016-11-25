package com.maplebox.nail.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.maplebox.nail.task.TaskErrorHandler;

@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.maplebox.nail.task"})
public class SchedulerConfig implements SchedulingConfigurer {
	static Logger log = LoggerFactory.getLogger(SchedulerConfig.class);
	
	@Autowired
	private TaskScheduler taskScheduler;
	
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler);
    }

    @Bean(destroyMethod = "shutdown")
    public TaskScheduler taskScheduler() {
    	ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    	taskScheduler.setPoolSize(10);
    	taskScheduler.setErrorHandler(new TaskErrorHandler());
        return taskScheduler;
    }
}
