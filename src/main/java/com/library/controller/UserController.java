package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping(value = { "/user" })
	public User getById(@RequestParam int userId) {
		return service.findById(userId);
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public void saveUser(@RequestBody User user) {
		service.save(user);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public void removeUser(@RequestParam int borrowerId) {
		service.delete(borrowerId);
	}
}
