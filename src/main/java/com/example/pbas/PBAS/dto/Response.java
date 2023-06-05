package com.example.pbas.PBAS.dto;

public class Response {

	private String message;
	private Integer status;
	private String token;

	public Response(String message, Integer status, String token) {
		this.message = message;
		this.status = status;
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}