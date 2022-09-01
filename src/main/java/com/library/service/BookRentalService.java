package com.library.service;

import java.util.List;

import com.library.exception.BookCopyException;
import com.library.exception.BookLimitException;
import com.library.model.BookRental;
import com.library.repository.BookRentalRepository;

public interface BookRentalService {

	public List<BookRental> getAll();

	public BookRental get(int id);
	
	public BookRental addNew(BookRental rental);

	public BookRental save(BookRental rental);

	public void borrowBooks(List<Integer> bookIds, int borrowerId) throws BookLimitException, BookCopyException;

	public void setRentalRepository(BookRentalRepository rentalRepository);
	
}
