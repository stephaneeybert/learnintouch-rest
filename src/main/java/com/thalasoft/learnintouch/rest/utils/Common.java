package com.thalasoft.learnintouch.rest.utils;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;

public class Common {

    static public HttpHeaders createAuthenticationHeaders(String usernamePassword) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        byte[] encodedAuthorisation = Base64.encode(usernamePassword.getBytes());
        httpHeaders.add("Authorization", "Basic " + new String(encodedAuthorisation));
        return httpHeaders;
    }

}
