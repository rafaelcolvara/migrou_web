package com.migrou.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@EqualsAndHashCode(callSuper=false)
@ToString
public class CustomAuthenticationException extends AuthenticationException {
	
	private HttpStatus httpStatus; 
	  
	public CustomAuthenticationException(String msg) {
		super(msg);
	}

	public CustomAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public CustomAuthenticationException(String msg, HttpStatus status) {
		super(msg);
		this.httpStatus = status;
	}
	
	public CustomAuthenticationException(String msg, Throwable ex, HttpStatus status) {
		super(msg, ex);
		this.httpStatus = status;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	
}
