package com.library.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.exception.BookCopyException;
import com.library.exception.BookLimitException;
import com.library.model.BookItem;
import com.library.model.BookRental;
import com.library.model.User;
import com.library.repository.BookRentalRepository;
import com.library.service.BookRentalService;
import com.library.service.BookService;
import com.library.service.UserService;

@Service
public class BookRentalServiceImpl implements BookRentalService {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookRentalRepository rentalRepository;

	@Override
	public List<BookRental> getAll() {
		return rentalRepository.findAll();
	}

	@Override
	public BookRental get(int id) {
		return rentalRepository.findById(id).get();
	}

	@Override
	public BookRental addNew(BookRental rental) {
		rental.setCreationDate(LocalDate.now());
		return rentalRepository.save(rental);
	}

	@Override
	public BookRental save(BookRental rental) {
		return rentalRepository.save(rental);
	}

	@Override
	public void borrowBooks(List<Integer> bookIds, int borrowerId) throws BookLimitException, BookCopyException {

		User borrower = userService.findById(borrowerId);

		if (borrower.getTotalBooks() >= 2) {
			throw new BookLimitException("You can only borrow 2 books.");
		}

		List<BookItem> books = bookService.get(bookIds);
		List<BookRental> rentals = rentalRepository.findRentedBooksByBorrowerId(borrowerId);
		List<BookItem> rentedBooks = new ArrayList<BookItem>();
		rentals.stream().forEach(t -> rentedBooks.addAll(t.getRentedBooks()));

		if (rentedBooks.size() == 0) {
			doBorrowBooks(borrower, books);
		} else {
			for (BookItem bookItem : books) {
				for (int i = 0; i < rentedBooks.size(); i++) {
					if (copyOfBookExsists(rentedBooks.get(i), bookItem)) {
						throw new BookCopyException("User can have only 1 copy of the book!");
					} else {
						doBorrowBooks(borrower, books);
					}
				}
			}
		}

	}

	private boolean copyOfBookExsists(BookItem rentedBook, BookItem bookItem) {
		return bookItem.getAuthor() == rentedBook.getAuthor() && bookItem.getTitle() == rentedBook.getTitle();
	}

	private void doBorrowBooks(User borrower, List<BookItem> books) {
		books.stream().filter(BookItem::isAvailable).collect(Collectors.toList());
		books.stream().forEach(b -> b.setAvailable(false));
		BookRental bookRent = new BookRental();
		bookRent.setCreationDate(LocalDate.now());
		bookRent.setUser(borrower);
		bookRent.setRentedBooks(books);
		borrower.setTotalBooks(borrower.getTotalBooks() + books.size());
		this.save(bookRent);

	}
	
	public void setRentalRepository(BookRentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}
}
