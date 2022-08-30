package com.library.model;

import java.time.LocalDate;

public class BookRental {

	private LocalDate creationDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private String bookItemId;
	private String borrowerId;

	public BookRental() {
		super();
	}

	public BookRental(LocalDate creationDate, LocalDate dueDate, LocalDate returnDate, String bookItemId,
			String borrowerId) {
		super();
		this.creationDate = creationDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
		this.bookItemId = bookItemId;
		this.borrowerId = borrowerId;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getBookItemId() {
		return bookItemId;
	}

	public void setBookItemId(String bookItemId) {
		this.bookItemId = bookItemId;
	}

	public String getMemberId() {
		return borrowerId;
	}

	public void setMemberId(String memberId) {
		this.borrowerId = memberId;
	}
}
