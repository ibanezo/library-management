package com.library.exception;

public class BookLimitException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookLimitException() {
		super();
	}

	public BookLimitException(String message) {
		super(message);
	}

}
