package com.maplebox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index() {
		return "Admin Index";
	}
}
