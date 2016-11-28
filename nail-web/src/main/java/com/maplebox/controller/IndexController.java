package com.maplebox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController {
	static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Value("${app.version}")
	private String appVersion;
	
	@GetMapping(path = "index")
	public String index(Model model) {
		log.info("start index");
		log.info("app version = {}", appVersion);
		
		model.addAttribute("appVersion", appVersion);
		return "index";
	}
	
	@GetMapping(path = "systemjs.config.js")
	public String systemjs(Model model) {
		log.info("systemjs.config");
		
		model.addAttribute("appVersion", appVersion);
		return "systemjs.config";
	}
}
