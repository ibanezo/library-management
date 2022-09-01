package com.library.service;

import java.util.List;

import com.library.model.BookItem;
import com.library.repository.BookRepository;

public interface BookService {

	public List<BookItem> getAll();
	
	public List<BookItem> getAllAvailable(boolean available);

	List<BookItem> get(List<Integer> ids);

	public BookItem save(BookItem book);

	public BookItem findById(int bookId);

	public void delete(int bookId);

	public void setRepository(BookRepository repository);

}
