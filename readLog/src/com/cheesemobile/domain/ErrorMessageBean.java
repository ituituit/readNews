package com.cheesemobile.domain;

public class ErrorMessageBean {

	private String message;
	private String time;
	private int errorType;
	
	public ErrorMessageBean(String message, String time, int errorType) {
		super();
		this.message = message;
		this.time = time;
		this.errorType = errorType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	
}
