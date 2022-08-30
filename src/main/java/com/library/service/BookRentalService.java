package com.library.service;

import com.library.exception.BookLimitException;
import com.library.exception.BookNotAvailableException;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;

public interface BookRentalService {

	public void borrowBooks(int bookId, int borrowerId) throws BookLimitException, BookNotAvailableException;

	public void setBookRepository(BookRepository bookRepository);

	public void setUserRepository(UserRepository userRepository);
}
