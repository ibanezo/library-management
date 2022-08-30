package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.exception.BookLimitException;
import com.library.exception.BookNotAvailableException;
import com.library.service.BookRentalService;

@RestController
public class BookRentalController {

	@Autowired
	BookRentalService service;
	
	@RequestMapping(value = "/borrow", method = RequestMethod.POST)
	public void borrowBook(@RequestParam int bookId, @RequestParam int borrowerId) throws BookLimitException, BookNotAvailableException {
		service.borrowBooks(bookId, borrowerId);
	}
	
	
}
