package com.maplebox.controller.admin;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maplebox.domain.DataResult;
import com.maplebox.domain.ResultCode;

@RestController
@RequestMapping(path = "/admin/photo")
public class PhotoController {
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public DataResult<Object> listPhoto() throws JsonProcessingException, IOException {
		
		return new DataResult<Object>(true, ResultCode.SUCCESS, null);
	}
}
