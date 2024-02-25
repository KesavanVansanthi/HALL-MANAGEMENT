package com.Exceptions;

public class InputInvalidException extends Exception{
	/**
	 * User Defined Exception for invalid Values
	 */
	private static final long serialVersionUID = 1L;

	public InputInvalidException(String e) {
		super(e);
	}
}
