package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.BookItem;
import com.library.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService service;

	@GetMapping(value = { "/", "/books" })
	public List<BookItem> getAllBooks() {
		return service.getAll();
	}

	@GetMapping(value = { "/book" })
	public BookItem getById(@RequestParam int bookId) {
		return service.findById(bookId);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveBook(@RequestBody BookItem book) {
		service.save(book);
	}
	
}
