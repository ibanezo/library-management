package com.library.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.model.BookRental;

@Transactional
public interface BookRentalRepository extends JpaRepository<BookRental, Integer>{

	List<BookRental> findRentedBooksByBorrowerId(int borrowerId);
	
	BookRental findByBorrowerId(int borrowerId);
}
