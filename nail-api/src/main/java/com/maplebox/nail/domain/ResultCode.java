package com.maplebox.nail.domain;

public enum ResultCode {
	SUCCESS("00000", "成功"),
	BASE_VALID_ERROR("00001", "基本驗證錯誤"),
	NOT_FOUND_PAGE("00002", "頁面不存在"),
	SERVER_ERROR("00003", "伺服器錯誤");
	
	private String code;
	private String message;
	
	ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
