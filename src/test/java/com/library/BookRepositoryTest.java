package com.library;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.library.model.Book;
import com.library.repository.BookRepository;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	BookRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void test_empty_library() {
		List<Book> emptyList = repository.findAll();
		assertThat(emptyList).isEmpty();
	}

	@Test
	public void test_save_one_book() {
		Book book = repository.save(new Book("Title test", "Author test"));
		assertThat(book).hasFieldOrPropertyWithValue("title", "Title test");
	}

	@Test
	public void test_save_multiple_books() {
		Book book1 = new Book("Book1", "Author1");
		entityManager.persist(book1);
		Book book2 = new Book("Book2", "Author2");
		entityManager.persist(book2);

		List<Book> books = repository.findAll();
		assertThat(books).hasSize(2).contains(book1, book2);
	}

}
