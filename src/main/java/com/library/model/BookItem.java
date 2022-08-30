package com.library.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BookItem extends Book {
	
	private LocalDate borrowDate;
	private boolean available;

	@JsonIgnore
	@ManyToOne
	private User borrower;

	public BookItem() {}
	public BookItem(LocalDate borrowDate, boolean available, User borrower) {
		super();
		this.borrowDate = borrowDate;
		this.available = available;
		this.borrower = borrower;
	}


	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public User getBorrower() {
		return borrower;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}

}
