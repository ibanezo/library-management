package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Book;
import com.library.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService service;

	@GetMapping(value = { "/", "/books" })
	public List<Book> getAllBooks() {
		return service.getAll();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveBook(@RequestBody Book book) {
		service.save(book);
	}
}
