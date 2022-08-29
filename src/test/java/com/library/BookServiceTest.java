package com.library;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;

@SpringBootTest
public class BookServiceTest {

	@MockBean
	BookRepository repositoryMock;

	@Autowired
	BookService service;

	@Test
	public void test_empty_library() {
		List<Book> emptyList = new ArrayList<Book>();
		Mockito.when(repositoryMock.findAll()).thenReturn(emptyList);

		service.setRepository(repositoryMock);
		service.getAll();

		Mockito.verify(repositoryMock, Mockito.times(1)).findAll();
	}

	@Test
	public void test_save_to_library() {
		Book book = new Book();
		book.setTitle("Test title");
		Mockito.when(repositoryMock.save(Mockito.any(Book.class))).thenReturn(book);

		service.setRepository(repositoryMock);
		service.save(book);

		Mockito.verify(repositoryMock, Mockito.times(1)).save(book);
		assertEquals("Test title", book.getTitle());
	}

}
