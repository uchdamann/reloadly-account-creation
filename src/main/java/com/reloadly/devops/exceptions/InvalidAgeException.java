package com.reloadly.devops.exceptions;

import java.io.Serializable;

public class InvalidAgeException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 4493683580747471021L;
	private static final String MESSAGE="You are not of age. Register under a guardian.";
	
	public InvalidAgeException()
    {
        super(MESSAGE);
    }

    public InvalidAgeException(Throwable cause)
    {
        super(MESSAGE, cause);
    }

    public InvalidAgeException(String message)
    {
        super(message);
    }

    public InvalidAgeException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
