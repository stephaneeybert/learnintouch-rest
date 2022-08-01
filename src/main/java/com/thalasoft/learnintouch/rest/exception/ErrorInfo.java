package com.thalasoft.learnintouch.rest.exception;

public class ErrorInfo {

	private String url;
	private String httpStatus;
	private String errorCode;
	private String message;
	private String developerMessage;

	public ErrorInfo(String url, String message) {
		this.url = url;
		this.message = message;
	}

	public ErrorInfo(String url, String httpStatus, String errorCode, String message, String developerMessage) {
		this.url = url;
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
		this.developerMessage = developerMessage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

}