package com.maplebox.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

import com.maplebox.config.TransactionConfig;
import com.maplebox.service.EmployeeService;

public class TestCase {
	static Logger log = LoggerFactory.getLogger(TestCase.class);
	
	@Test
	public void transaction() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TransactionConfig.class);
		try {
			EmployeeService service = applicationContext.getBean(EmployeeService.class);
			service.findAll().forEach(e -> log.debug("{}", e));
		} finally {
			if (applicationContext != null) {
				applicationContext.close();
			}
		}
	}
}
