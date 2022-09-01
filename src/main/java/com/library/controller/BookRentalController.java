package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.exception.BookCopyException;
import com.library.exception.BookLimitException;
import com.library.service.BookRentalService;

@RestController
public class BookRentalController {

	@Autowired
	private BookRentalService service;

	@PostMapping(value = "/books/rentals")
	public void borrowBook(@RequestParam List<Integer> bookIds, @RequestParam int borrowerId) throws BookLimitException, BookCopyException {
		service.borrowBooks(bookIds, borrowerId);
	}

}
