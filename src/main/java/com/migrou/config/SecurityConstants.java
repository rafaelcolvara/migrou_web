package com.migrou.config;

public class SecurityConstants {

    static final String SECRET = "SenhaSuperSecret@";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/pessoas/login";
    static final Long EXPIRATION_TIME = 86400000L; //um dia

}
