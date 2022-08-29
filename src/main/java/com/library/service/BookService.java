package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.Book;
import com.library.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;
	
	public List<Book> getAll() {
		return repository.findAll();
	}
	
	public Book save(Book book) {
		return repository.save(book);
	}

	public void setRepository(BookRepository repository) {
		this.repository = repository;
	}
}
