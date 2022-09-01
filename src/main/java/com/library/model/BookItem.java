package com.library.model;

import javax.persistence.Entity;

@Entity
public class BookItem extends Book {

	private boolean available;

	public BookItem() {
	}

	public BookItem(boolean available) {
		super();
		this.available = available;
	}


	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
