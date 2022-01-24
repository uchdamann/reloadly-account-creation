package com.reloadly.devops.exceptions;

public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = 6980400168899103074L;
	private static final String MESSAGE = "User does not exist";

	public InvalidUserException()
    {
        super(MESSAGE);
    }

    public InvalidUserException(Throwable cause)
    {
        super(MESSAGE, cause);
    }

    public InvalidUserException(String message)
    {
        super(message);
    }

    public InvalidUserException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
