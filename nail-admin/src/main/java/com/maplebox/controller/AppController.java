package com.maplebox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/app")
public class AppController {
	static Logger log = LoggerFactory.getLogger(AppController.class);
	
	@Value("${app.version}")
	private String appVersion;
	
	@Value("${app.mode}")
	private String appMode;
}
