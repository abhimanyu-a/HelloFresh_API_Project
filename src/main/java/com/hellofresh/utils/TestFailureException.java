package com.hellofresh.utils;

public class TestFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TestFailureException(String s)
	{
		// Call constructor of parent Exception
		super(s);
	}

}

