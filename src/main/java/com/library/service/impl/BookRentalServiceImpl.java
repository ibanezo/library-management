package com.library.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.exception.BookLimitException;
import com.library.exception.BookNotAvailableException;
import com.library.model.BookItem;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import com.library.service.BookRentalService;

@Service
public class BookRentalServiceImpl implements BookRentalService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void borrowBooks(int bookId, int borrowerId) throws BookLimitException, BookNotAvailableException {
		User borrower = userRepository.findById(borrowerId).get();
		if (borrower.getTotalBooks() >= 2) {
			throw new BookLimitException("You can only borrow 2 books.");
		}

		BookItem book = bookRepository.findById(bookId).get();
		if (book.isAvailable()) {
			book.setBorrower(borrower);
			book.setAvailable(false);
			book.setBorrowDate(LocalDate.now());

			List<BookItem> borrowedBooks = new ArrayList<BookItem>();
			borrowedBooks.add(book);
			// bookRepository.delete(book);

			borrower.setBooks(borrowedBooks);
			borrower.setTotalBooks(borrower.getTotalBooks() + 1);
			userRepository.save(borrower);
		} else {
			throw new BookNotAvailableException("This book is not available.");
		}
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
