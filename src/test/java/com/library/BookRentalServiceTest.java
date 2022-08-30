package com.library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.exception.BookLimitException;
import com.library.exception.BookNotAvailableException;
import com.library.model.BookItem;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import com.library.service.BookRentalService;

@SpringBootTest
public class BookRentalServiceTest {

	@MockBean
	UserRepository userRepositoryMock;

	@MockBean
	BookRepository bookRepositoryMock;

	@Autowired
	BookRentalService service;

	@Test
	public void test_borrow_book() throws BookLimitException, BookNotAvailableException {

		BookItem book = new BookItem();
		book.setTitle("Title test");
		book.setBookId(123);
		book.setAvailable(true);
		bookRepositoryMock.save(book);
		assertThat(book).hasFieldOrPropertyWithValue("title", "Title test");

		User user = new User();
		user.setDisplayName("Test User");
		userRepositoryMock.save(user);
		assertThat(user).hasFieldOrPropertyWithValue("displayName", "Test User");

		Mockito.when(userRepositoryMock.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(bookRepositoryMock.findById(book.getBookId())).thenReturn(Optional.of(book));

		service.setUserRepository(userRepositoryMock);
		service.borrowBooks(book.getBookId(), user.getUserId());

		Mockito.verify(userRepositoryMock, Mockito.times(1)).findById(user.getUserId());
		Mockito.verify(bookRepositoryMock, Mockito.times(1)).findById(book.getBookId());
		Mockito.verify(userRepositoryMock, Mockito.times(2)).save(user);
		Mockito.verify(bookRepositoryMock, Mockito.times(1)).save(book);
	}
	
	@Test
	public void test_borrow_not_available_book() throws BookLimitException, BookNotAvailableException {
		BookItem book = new BookItem();
		book.setTitle("Title test");
		book.setAvailable(false);
		bookRepositoryMock.save(book);
		assertThat(book).hasFieldOrPropertyWithValue("title", "Title test");

		User user = new User();
		user.setDisplayName("Test User");
		userRepositoryMock.save(user);
		assertThat(user).hasFieldOrPropertyWithValue("displayName", "Test User");

		Mockito.when(userRepositoryMock.findById(user.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(bookRepositoryMock.findById(book.getBookId())).thenReturn(Optional.of(book));

		service.setUserRepository(userRepositoryMock);
		
		Exception exception = assertThrows(BookNotAvailableException.class, () -> {
			service.borrowBooks(book.getBookId(), user.getUserId());

	    });

		Mockito.verify(userRepositoryMock, Mockito.times(1)).findById(user.getUserId());
		Mockito.verify(bookRepositoryMock, Mockito.times(1)).findById(book.getBookId());
		Mockito.verify(userRepositoryMock, Mockito.times(1)).save(user);
		Mockito.verify(bookRepositoryMock, Mockito.times(1)).save(book);
		
	    String expectedMessage = "This book is not available.";
	    String actualMessage = exception.getMessage();

	    Assertions.assertTrue(actualMessage.contains(expectedMessage));

	}

}
