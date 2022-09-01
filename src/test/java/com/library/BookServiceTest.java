package com.library;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.model.BookItem;
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
		List<BookItem> emptyList = new ArrayList<BookItem>();
		Mockito.when(repositoryMock.findAll()).thenReturn(emptyList);

		service.setRepository(repositoryMock);
		service.getAll();

		Mockito.verify(repositoryMock, Mockito.times(1)).findAll();
	}

	@Test
	public void test_save_to_library() {
		BookItem book = new BookItem();
		book.setTitle("Test title");
		Mockito.when(repositoryMock.save(Mockito.any(BookItem.class))).thenReturn(book);

		service.setRepository(repositoryMock);
		service.save(book);

		Mockito.verify(repositoryMock, Mockito.times(1)).save(book);
		assertEquals("Test title", book.getTitle());
	}

	@Test
	public void test_find_by_id() {
		BookItem book = new BookItem();
		book.setTitle("Title Find");
		book.setId(123);
		Mockito.when(repositoryMock.findById(book.getId())).thenReturn(Optional.of(book));

		service.setRepository(repositoryMock);
		service.save(book);

		assertEquals(123, repositoryMock.findById(book.getId()).get().getId());
	}
	
	@Test
	public void test_remove_by_id() {
		BookItem book = new BookItem();
		book.setTitle("Title Find");
		book.setId(123);

		service.setRepository(repositoryMock);
		service.delete(book.getId());
		
		Mockito.verify(repositoryMock).deleteById(Mockito.any());
	}

}
