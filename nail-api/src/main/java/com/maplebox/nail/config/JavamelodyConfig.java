package com.maplebox.nail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:net/bull/javamelody/monitoring-spring-aspectj.xml")
public class JavamelodyConfig {

}
