package com.maplebox.config;

import java.text.ParseException;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import com.maplebox.task.TaskErrorHandler;

@Configuration
@EnableAsync
//@EnableScheduling
@ImportResource(locations = {
	"classpath:quartz.xml"
})
@ComponentScan(basePackages = {"com.maplebox.task"})
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
    
    /**
     * 暫不使用此方法
     * @param simpleJobDetail
     */
    public void dynamicQuartzScheduler(MethodInvokingJobDetailFactoryBean simpleJobDetail) {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = schedFact.getScheduler();
            JobDetail details = simpleJobDetail.getObject();

            CronTriggerImpl trigger = new CronTriggerImpl();
            trigger.setName("T1");

            try {
                trigger.setCronExpression("0/10 * * * * ?");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            trigger.setDescription("desc");
            scheduler.scheduleJob(details,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
