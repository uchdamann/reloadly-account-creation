package com.reloadly.devops.exceptions;

public class UsernameMismatchException extends RuntimeException {
	private static final long serialVersionUID = -2309134083987466136L;
	private static final String MESSAGE = "Username mistmatch exception";
	
	public UsernameMismatchException() {
		super(MESSAGE);
	}
}