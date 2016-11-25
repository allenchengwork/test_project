package com.maplebox.nail.domain;

public class LoginToken {
	private String name;
	private String token;
	
	public LoginToken(String name, String token) {
		super();
		this.name = name;
		this.token = token;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
