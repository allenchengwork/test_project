package com.maplebox.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TaskLog {
	private static Logger taskLog = LoggerFactory.getLogger("com.maplebox.task");
	
	@Pointcut("execution(* com.maplebox.task..*.*(..))")
	public void taskLog() { }
	
	@AfterThrowing(
            pointcut = "taskLog()",
            throwing= "error")
    public void taskLogAfterThrowing(JoinPoint joinPoint, Throwable error) {
		taskLog.info("logAfterThrowing() is running!");
		taskLog.info("hijacked : " + joinPoint.getSignature().getName());
		taskLog.info("Exception : " + error);
		taskLog.info("******");

    }
}
