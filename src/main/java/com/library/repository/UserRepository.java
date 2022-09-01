package com.library.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{
}
