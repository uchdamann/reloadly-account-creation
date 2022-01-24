package com.reloadly.devops.exceptions;

public class AppException extends RuntimeException{
	private static final long serialVersionUID = -920079447885155703L;
	private static final String MESSAGE = "Operation failed";

	public AppException()
    {
        super(MESSAGE);
    }

    public AppException(Throwable cause)
    {
        super(MESSAGE, cause);
    }

    public AppException(String message)
    {
        super(message);
    }

    public AppException(String message, Throwable cause)
    {
        super(message, cause);
    }
}