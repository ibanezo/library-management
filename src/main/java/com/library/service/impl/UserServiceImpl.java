package com.library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public User findById(int borrowerId) {
		return repository.findById(borrowerId).get();
	}

	@Override
	public void delete(int borrowerId) {
		repository.deleteById(borrowerId);
	}

	@Override
	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

}
