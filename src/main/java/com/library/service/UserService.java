package com.library.service;

import java.util.List;

import com.library.model.User;
import com.library.repository.UserRepository;

public interface UserService {
	
	public List<User> getAll();

	public User save(User user);

	public User findById(int borrowerId);

	public void delete(int borrowerId);

	public void setRepository(UserRepository repository);
}
