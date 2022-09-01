package com.library.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.model.BookItem;

@Transactional
public interface BookRepository extends JpaRepository<BookItem, Integer>{
	public List<BookItem> findAllByAvailable(boolean available);	
}
