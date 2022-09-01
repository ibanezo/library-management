package com.library;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.library.model.BookItem;
import com.library.repository.BookRepository;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	BookRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void test_empty_library() {
		List<BookItem> emptyList = repository.findAll();
		assertThat(emptyList).isEmpty();
	}

	@Test
	public void test_save_one_book() {
		BookItem book = new BookItem();
		book.setTitle("Title test");
		repository.save(book);
		assertThat(book).hasFieldOrPropertyWithValue("title", "Title test");
	}

	@Test
	public void test_save_multiple_books() {
		BookItem book1 = new BookItem();
		book1.setTitle("Book1");
		book1.setAuthor("Author1");
		entityManager.persist(book1);
		BookItem book2 = new BookItem();
		book2.setTitle("Book2");
		book2.setAuthor("Author2");
		entityManager.persist(book2);

		List<BookItem> books = repository.findAll();
		assertThat(books).hasSize(2).contains(book1, book2);
	}

	@Test
	public void test_find_by_available_books() {
		
		BookItem book1 = new BookItem();
		book1.setTitle("Book1");
		book1.setAuthor("Author1");
		book1.setAvailable(true);
		
		BookItem book2 = new BookItem();
		book2.setTitle("Book2");
		book2.setAuthor("Author2");
		book2.setAvailable(false);
		
		entityManager.persist(book1);
		entityManager.persist(book2);

		List<BookItem> books = repository.findAllByAvailable(true);
		assertThat(books).hasSize(1).contains(book1);
	}
}
