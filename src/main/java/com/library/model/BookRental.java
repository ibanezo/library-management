package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "BOOK_RENTAL")
public class BookRental {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate creationDate;
	private LocalDate returnDate;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "rented_books", joinColumns = @JoinColumn(name = "rental_id", referencedColumnName = "id"), 
				inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
	private List<BookItem> rentedBooks;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "borrower_id")
	private User borrower;

	public BookRental() {
		super();
	}

	public BookRental(LocalDate creationDate, LocalDate returnDate) {
		super();
		this.creationDate = creationDate;
		this.returnDate = returnDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getBorrower() {
		return borrower;
	}

	public void setBorrower(User borrower) {
		this.borrower = borrower;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public User getUser() {
		return borrower;
	}

	public void setUser(User borrower) {
		this.borrower = borrower;
	}

	public List<BookItem> getRentedBooks() {
		if(rentedBooks == null) {
			return new ArrayList<BookItem>();
		}
		return rentedBooks;
	}

	public void setRentedBooks(List<BookItem> rentedBooks) {
		this.rentedBooks = rentedBooks;
	}

}
