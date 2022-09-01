package com.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.BookItem;
import com.library.repository.BookRepository;
import com.library.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<BookItem> getAll() {
		return bookRepository.findAll();
	}

	@Override
	public List<BookItem> getAllAvailable(boolean available) {
		return bookRepository.findAllByAvailable(available);
	}
	
	@Override
	public List<BookItem> get(List<Integer> ids) {
		return bookRepository.findAllById(ids);
	}
	
	@Override
	public BookItem save(BookItem book) {
		book.setAvailable(true);
		return bookRepository.save(book);
	}

	@Override
	public BookItem findById(int bookId) {
		return bookRepository.findById(bookId).get();
	}

	@Override
	public void delete(int bookId) {
		bookRepository.deleteById(bookId);
	}

	public void setRepository(BookRepository repository) {
		this.bookRepository = repository;
	}

}
