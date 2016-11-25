package com.maplebox.nail.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	private static Logger emailLog = LoggerFactory.getLogger("com.maplebox.nail.email");
	
	@Pointcut("execution(* com.maplebox.nail.service.EmailService.sendEmail(..))")
	public void emailLog() { }
	
	@Before("emailLog()")
    public void emailLogBefore(JoinPoint joinPoint) {
		emailLog.info("logBefore() is running!");
        //下面方法會抓到代理器名稱

		emailLog.info("hijacked : " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        for(int i = 0 ;i < args.length ; i++){
        	emailLog.info("params["+i+"]:"+ args[i].toString());
        } 
        emailLog.info("******");
    }

	@Around("emailLog()")
    public Object emailLogAround(ProceedingJoinPoint pjp) throws Throwable{
		emailLog.info("emailLogAround before");
        Object val = pjp.proceed();
        emailLog.info("emailLogAround after");
        return val;
    }

	@After("emailLog()")
    public void emailLogAfter(JoinPoint joinPoint) {
        emailLog.info("logAfter() is running!");
        emailLog.info("hijacked : " + joinPoint.getSignature().getName());
        emailLog.info("******");
    }
	
	@AfterReturning(
            pointcut = "emailLog()",
            returning= "result")
    public void emailLogAfterReturning(JoinPoint joinPoint, Object result) {
        emailLog.info("logAfterReturning() is running!");
        emailLog.info("hijacked : " + joinPoint.getSignature().getName());
        emailLog.info("Method returned value is : " + result);
        emailLog.info("******");

    }

    @AfterThrowing(
            pointcut = "emailLog()",
            throwing= "error")
    public void emailLogAfterThrowing(JoinPoint joinPoint, Throwable error) {
        emailLog.info("logAfterThrowing() is running!");
        emailLog.info("hijacked : " + joinPoint.getSignature().getName());
        emailLog.info("Exception : " + error);
        emailLog.info("******");

    }
}
