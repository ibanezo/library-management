package com.library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

	private BookItem book1;
	private BookItem book2;
	private BookItem book3;
	private User user;
	private BookRental bookRent;
	
	@BeforeEach
    void setUp() {
		book1 = createBookItem("Title Test1", true);
		book2 = createBookItem("Title Test2", true);
		book3 = createBookItem("Title Test2", true);
		user = createUser();
		assertThat(book1).hasFieldOrPropertyWithValue("title", "Title Test1");
		assertThat(book2).hasFieldOrPropertyWithValue("title", "Title Test2");
		assertThat(user).hasFieldOrPropertyWithValue("displayName", "Test User");
    }
	
	@Test
	public void test_borrow_book() throws BookLimitException, BookCopyException {
		bookRent = new BookRental();
		
		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.get(Arrays.asList(book1.getId()))).thenReturn(Arrays.asList(book1));
		Mockito.when(rentalRepositoryMock.findRentedBooksByBorrowerId(user.getId()))
				.thenReturn(Arrays.asList(bookRent));

		rentalService.setRentalRepository(rentalRepositoryMock);

		rentalService.borrowBooks(Arrays.asList(book1.getId()), user.getId());

		Mockito.verify(userServiceMock, Mockito.times(1)).findById(user.getId());
		Mockito.verify(userServiceMock, Mockito.times(1)).save(user);
	}

	@Test
	public void test_borrow_two_books() throws BookLimitException, BookCopyException {
		bookRent = new BookRental();

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
		bookRent = createBookRental(Arrays.asList(book1), user);

		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.get(Arrays.asList(book1.getId()))).thenReturn(Arrays.asList(book1));
		Mockito.when(rentalRepositoryMock.findRentedBooksByBorrowerId(user.getId()))
				.thenReturn(Arrays.asList(bookRent));

		rentalService.setRentalRepository(rentalRepositoryMock);

		Exception exception = assertThrows(BookCopyException.class, () -> {
			rentalService.borrowBooks(Arrays.asList(book1.getId()), user.getId());

		});

		String expectedMessage = "User can have only 1 copy of the book!";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void test_return_two_books() {
		bookRent = createBookRental(Arrays.asList(book2,book3), user);
		
		Mockito.when(rentalRepositoryMock.findByBorrowerId(user.getId())).thenReturn(bookRent);
		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.save(book2)).thenReturn(book2);
		Mockito.when(bookServiceMock.save(book3)).thenReturn(book3);
		Mockito.when(rentalRepositoryMock.save(bookRent)).thenReturn(bookRent);
		
		rentalService.returnBooks(Arrays.asList(book3.getId()), user.getId());
		
		assertThat(book3.getId()).isNotNull();
		assertThat(bookRent.getId()).isNotNull();
	}
	
	@Test
	public void test_return_one_book() {
		bookRent = createBookRental(Arrays.asList(book1), user);
		
		Mockito.when(rentalRepositoryMock.findByBorrowerId(user.getId())).thenReturn(bookRent);
		Mockito.when(userServiceMock.findById(user.getId())).thenReturn(user);
		Mockito.when(bookServiceMock.save(book1)).thenReturn(book1);
		Mockito.when(rentalRepositoryMock.save(bookRent)).thenReturn(bookRent);
		
		rentalService.returnBooks(Arrays.asList(book1.getId()), user.getId());
		
		assertThat(book1.getId()).isNotNull();
		assertThat(bookRent.getId()).isNotNull();
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

	private BookRental createBookRental(List<BookItem> books, User user) {
		BookRental bookRent = new BookRental();
		bookRent.setCreationDate(LocalDate.now());
		bookRent.setUser(user);
		bookRent.setRentedBooks(books	);
		return bookRent;
	}

}
