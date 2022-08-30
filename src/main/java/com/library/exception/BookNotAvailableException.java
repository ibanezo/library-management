package com.library.exception;

public class BookNotAvailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookNotAvailableException() {
		super();
	}

	public BookNotAvailableException(String message) {
		super(message);
	}
}
