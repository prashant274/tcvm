package com.yash.ytcvm.exception;

public class NoValuePresentException extends RuntimeException {

	public NoValuePresentException() {
		super();
	}
	
	public NoValuePresentException(String errorMessage) {
		super(errorMessage);
	}
}
