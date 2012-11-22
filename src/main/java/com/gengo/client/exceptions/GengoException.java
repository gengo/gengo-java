package com.gengo.client.exceptions;

@SuppressWarnings("serial")
/**
 * Encapsulates all exceptions thrown by the API 
 */
public class GengoException extends Exception
{
	public GengoException(String e)
	{
		super(e);
	}
	
	public GengoException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
	
}
