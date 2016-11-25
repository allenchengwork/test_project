package com.maplebox.nail.config;

import org.springframework.context.annotation.Bean;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@org.springframework.context.annotation.Configuration
public class TemplateConfig {
	@Bean
	public Configuration freemarkerConfiguration() {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setDefaultEncoding("UTF-8");
		
		ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "/template");
		cfg.setTemplateLoader(ctl);
		return cfg;
	}
}
