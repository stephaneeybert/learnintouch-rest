package com.thalasoft.learnintouch.rest.exception;

public class ErrorFormField {

	private String fieldName;
	private String fieldError;

	public ErrorFormField(String fieldName, String fieldError) {
		this.fieldName = fieldName;
		this.fieldError = fieldError;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldError() {
		return fieldError;
	}

	public void setFieldError(String fieldError) {
		this.fieldError = fieldError;
	}

}
