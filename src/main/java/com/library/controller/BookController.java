package com.library.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping(value = { "/books/" })
	public List<BookItem> getAllAvailableBooks(@RequestParam Boolean available) {
		return service.getAllAvailable(available);
	}
	
	@GetMapping(value = { "/books/{id}" })
	public BookItem getById(@PathParam("id") Integer bookId) {
		return service.findById(bookId);
	}
	
	@PostMapping(value = "/books")
	public void saveBook(@RequestBody BookItem book) {
		service.save(book);
	}	
	
}
