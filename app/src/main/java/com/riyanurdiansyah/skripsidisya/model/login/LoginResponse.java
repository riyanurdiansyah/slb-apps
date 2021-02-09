package com.riyanurdiansyah.skripsidisya.model.login;

import com.riyanurdiansyah.skripsidisya.model.login.LoginRequest;

public class LoginResponse {

	String status;
	String message;
	LoginRequest data;

	public LoginResponse(String status, String message, LoginRequest data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginRequest getData() {
		return data;
	}

	public void setData(LoginRequest data) {
		this.data = data;
	}
}