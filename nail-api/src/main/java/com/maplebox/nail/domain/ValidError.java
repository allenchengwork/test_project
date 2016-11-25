package com.maplebox.nail.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidError {
	private List<String> globalError = new ArrayList<>();
	private Map<String, Map<String, String>> fieldError = new HashMap<>();
	
	public List<String> getGlobalError() {
		return globalError;
	}
	public void setGlobalError(List<String> globalError) {
		this.globalError = globalError;
	}
	public Map<String, Map<String, String>> getFieldError() {
		return fieldError;
	}
	public void setFieldError(Map<String, Map<String, String>> fieldError) {
		this.fieldError = fieldError;
	}
}
