package com.maplebox.nail.domain;

public class DataResult<T> {
	private boolean success;
	private String code;
	private String message;
	private T data;
	
	public DataResult(boolean success, ResultCode resultCode, T data) {
		super();
		this.setSuccess(success);
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
		this.data = data;
	}
	
	public DataResult(boolean success, String code, String message, T data) {
		super();
		this.setSuccess(success);
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
