package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.User;
import com.library.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;

	@GetMapping(value = { "/users" })
	public List<User> getAllUsers() {
		return service.getAll();
	}
	
	@GetMapping(value = { "/users/{id}" })
	public User getById(@PathVariable("id") Integer userId) {
		return service.findById(userId);
	}

	@PostMapping(value = "/users")
	public void saveUser(@RequestBody User user) {
		service.save(user);
	}

	@DeleteMapping(value = "/users/{id}")
	public void removeUser(@PathVariable("id") Integer userId) {
		service.delete(userId);
	}
}
