package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.model.BookItem;

@Repository
public interface BookRepository extends JpaRepository<BookItem, Integer>{

}
