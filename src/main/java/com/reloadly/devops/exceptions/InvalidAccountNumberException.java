package com.reloadly.devops.exceptions;

import java.io.Serializable;

public class InvalidAccountNumberException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 6479485452836052090L;
	private static final String MESSAGE="Account number is invalid";
	
	public InvalidAccountNumberException()
    {
        super(MESSAGE);
    }

    public InvalidAccountNumberException(Throwable cause)
    {
        super(MESSAGE, cause);
    }

    public InvalidAccountNumberException(String message)
    {
        super(message);
    }

    public InvalidAccountNumberException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
