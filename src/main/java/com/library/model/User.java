package com.library.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	private String displayName;

	private String username;

	private String password;
	
	private String email;
	
	private int totalBooks;
	
	@OneToMany(mappedBy="borrower")
	private List<BookItem> books;

	public User() {
	}

	public User(int id, String displayName, String username, String password, String email) {
		super();
		this.userId = id;
		this.displayName = displayName;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<BookItem> getBooks() {
		return books;
	}

	public void setBooks(List<BookItem> books) {
		this.books = books;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getTotalBooks() {
		return totalBooks;
	}

	public void setTotalBooks(int totalBooks) {
		this.totalBooks = totalBooks;
	}
	
}
