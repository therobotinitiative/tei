package com.orbital3d.server.tei.error;

public class AuthenticationFailedExcetion extends RuntimeException
{

	/**
	 * Generated
	 */
	private static final long serialVersionUID = -1684606128568206034L;

	public AuthenticationFailedExcetion()
	{
		super();
	}

	public AuthenticationFailedExcetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationFailedExcetion(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AuthenticationFailedExcetion(String message)
	{
		super(message);
	}

	public AuthenticationFailedExcetion(Throwable cause)
	{
		super(cause);
	}

}
