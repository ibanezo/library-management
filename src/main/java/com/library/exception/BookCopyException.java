package com.library.exception;

public class BookCopyException extends Exception {
	private static final long serialVersionUID = 1L;

	public BookCopyException() {
		super();
	}

	public BookCopyException(String message) {
		super(message);
	}
}
