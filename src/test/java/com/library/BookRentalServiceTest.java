package com.library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.exception.BookCopyException;
import com.library.exception.BookLimitException;
import com.library.model.BookItem;
import com.library.model.BookRental;
import com.library.model.User;
import com.library.repository.BookRentalRepository;
import com.library.service.BookRentalService;
import com.library.service.BookService;
import com.library.service.UserService;

@SpringBootTest
public class BookRentalServiceTest {

	@MockBean
	UserService userServiceMock;

	@Autowired
	BookRentalService rentalService;

	@MockBean
	BookRentalRepository rentalRepositoryMock;

	@MockBean
	BookService bookServiceMock;

	@Test
	public void test_borrow_book() throws BookLimitException, BookCopyException {
		BookItem book = createBookItem("Title test", true);
		assertThat(book).hasFieldOrPropertyWithValue("title", "Title test");

		User user = createUser();
		assertThat(user).hasFieldOrPropertyWithValue("displayName", "Test User");

		BookRental bookRent = new BookRental();

		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.get(Arrays.asList(book.getId()))).thenReturn(Arrays.asList(book));
		Mockito.when(rentalRepositoryMock.findRentedBooksByBorrowerId(user.getId()))
				.thenReturn(Arrays.asList(bookRent));

		rentalService.setRentalRepository(rentalRepositoryMock);

		rentalService.borrowBooks(Arrays.asList(book.getId()), user.getId());

		Mockito.verify(userServiceMock, Mockito.times(1)).findById(user.getId());
		Mockito.verify(userServiceMock, Mockito.times(1)).save(user);
	}

	@Test
	public void test_borrow_two_books() throws BookLimitException, BookCopyException {
		BookItem book1 = createBookItem("Test1", true);
		assertThat(book1).hasFieldOrPropertyWithValue("title", "Test1");

		BookItem book2 = createBookItem("Test2", true);
		assertThat(book2).hasFieldOrPropertyWithValue("title", "Test2");

		User user = createUser();
		assertThat(user).hasFieldOrPropertyWithValue("displayName", "Test User");

		BookRental bookRent = new BookRental();

		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.get(Arrays.asList(book1.getId(), book2.getId())))
				.thenReturn(Arrays.asList(book1, book2));
		Mockito.when(rentalRepositoryMock.findRentedBooksByBorrowerId(user.getId()))
				.thenReturn(Arrays.asList(bookRent));

		rentalService.setRentalRepository(rentalRepositoryMock);
		rentalService.borrowBooks(Arrays.asList(book1.getId(), book2.getId()), user.getId());

		Mockito.verify(userServiceMock, Mockito.times(1)).findById(user.getId());
		Mockito.verify(userServiceMock, Mockito.times(1)).save(user);
		Mockito.verify(rentalRepositoryMock, Mockito.times(1)).findRentedBooksByBorrowerId(user.getId());
		Mockito.verify(bookServiceMock, Mockito.times(1)).get(Arrays.asList(book1.getId(), book2.getId()));
	}

	@Test
	public void test_borrow_two_copies_of_book() throws BookLimitException, BookCopyException {
		BookItem book = createBookItem("Test1", true);
		assertThat(book).hasFieldOrPropertyWithValue("title", "Test1");

		User user = createUser();
		assertThat(user).hasFieldOrPropertyWithValue("displayName", "Test User");

		BookRental bookRent = createBookRental(book, user);

		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.get(Arrays.asList(book.getId()))).thenReturn(Arrays.asList(book));
		Mockito.when(rentalRepositoryMock.findRentedBooksByBorrowerId(user.getId()))
				.thenReturn(Arrays.asList(bookRent));

		rentalService.setRentalRepository(rentalRepositoryMock);

		Exception exception = assertThrows(BookCopyException.class, () -> {
			rentalService.borrowBooks(Arrays.asList(book.getId()), user.getId());

		});

		String expectedMessage = "User can have only 1 copy of the book!";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	private User createUser() {
		User user = new User();
		user.setDisplayName("Test User");
		userServiceMock.save(user);
		return user;
	}

	private BookItem createBookItem(String title, boolean available) {
		BookItem book = new BookItem();
		book.setTitle(title);
		book.setId(123);
		book.setAvailable(available);
		bookServiceMock.save(book);
		return book;
	}

	private BookRental createBookRental(BookItem book, User user) {
		BookRental bookRent = new BookRental();
		bookRent.setCreationDate(LocalDate.now());
		bookRent.setUser(user);
		bookRent.setRentedBooks(Arrays.asList(book));
		return bookRent;
	}

}
