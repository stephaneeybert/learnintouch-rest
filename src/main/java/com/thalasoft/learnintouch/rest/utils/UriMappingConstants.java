package com.thalasoft.learnintouch.rest.utils;

public final class UriMappingConstants {

	public static final String PATH_SEPARATOR = "/";

	public static final String REL_FIRST = "first";
    public static final String REL_PREV = "prev";
    public static final String REL_NEXT = "next";
    public static final String REL_LAST = "last";

    public static final String GREETING = "greeting";
    public static final String HELLO = "hello";

    public static final String ADMINS = "admins";
    public static final String SEARCH = "search";    
    public static final String MODULES = "modules";
    
//    public static final class Singular {
//
//        public static final String ADMIN = "admin";
//        public static final String MODULE = "module";
//
//    }
    
    private UriMappingConstants() {
        throw new AssertionError();
    }
    
}
