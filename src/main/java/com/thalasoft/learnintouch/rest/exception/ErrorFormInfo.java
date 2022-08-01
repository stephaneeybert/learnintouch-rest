package com.thalasoft.learnintouch.rest.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorFormInfo {

    private String url;
    private String message;
    private List<ErrorFormField> fieldErrors = new ArrayList<ErrorFormField>();

    public ErrorFormInfo() {
    }

    public ErrorFormInfo(String url, String message) {
            this.url = url;
            this.message = message;
    }

    public ErrorFormInfo(List<ErrorFormField> fieldErrors, String url, String message) {
            this.fieldErrors = fieldErrors;
            this.url = url;
            this.message = message;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorFormField> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<ErrorFormField> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
    
    
}
