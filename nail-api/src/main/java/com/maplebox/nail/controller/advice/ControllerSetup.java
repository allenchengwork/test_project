package com.maplebox.nail.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerSetup {
	static Logger log = LoggerFactory.getLogger(ControllerSetup.class);
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		log.debug("ControllerSetup");
		StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringtrimmer);
    }
}
