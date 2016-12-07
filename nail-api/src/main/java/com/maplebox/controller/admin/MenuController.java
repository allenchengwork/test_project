package com.maplebox.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplebox.domain.DataResult;
import com.maplebox.domain.ResultCode;

@RestController
@RequestMapping(path = "/admin/menu")
public class MenuController {
	
	@Value("${admin.main.navigation}")
	private String mainNavigation;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@GetMapping(path = "mainNavigation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public DataResult<Object> mainNavigation() throws JsonProcessingException, IOException {
		JsonNode jsonNode = objectMapper.readTree(mainNavigation);
		
		return new DataResult<Object>(true, ResultCode.SUCCESS, jsonNode);
	}
}
