package com.thalasoft.learnintouch.rest.resource;

import org.springframework.hateoas.ResourceSupport;

public class Greeting extends ResourceSupport {

    private String message;

    public Greeting() {
    }

    public Greeting(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    
}
