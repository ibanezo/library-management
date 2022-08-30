package com.library;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.UserService;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	UserRepository repositoryMock;

	@Autowired
	UserService service;

	@Test
	public void test_no_users_created() {
		List<User> emptyList = new ArrayList<User>();
		Mockito.when(repositoryMock.findAll()).thenReturn(emptyList);

		service.setRepository(repositoryMock);
		service.getAll();

		Mockito.verify(repositoryMock, Mockito.times(1)).findAll();
	}

	@Test
	public void test_save_user() {
		User user = new User();
		user.setDisplayName("Test Name");
		Mockito.when(repositoryMock.save(Mockito.any(User.class))).thenReturn(user);

		service.setRepository(repositoryMock);
		service.save(user);

		Mockito.verify(repositoryMock, Mockito.times(1)).save(user);
		assertEquals("Test Name", user.getDisplayName());
	}

	@Test
	public void test_find_by_id() {
		User user = new User();
		user.setDisplayName("Test Name");
		user.setUserId(123);
		Mockito.when(repositoryMock.findById(user.getUserId())).thenReturn(Optional.of(user));

		service.setRepository(repositoryMock);
		service.save(user);

		assertEquals(123, repositoryMock.findById(user.getUserId()).get().getUserId());
	}
	
	@Test
	public void test_remove_by_id() {
		User book = new User();
		book.setDisplayName("Test Name");
		book.setUserId(123);

		service.setRepository(repositoryMock);
		service.delete(book.getUserId());
		
		Mockito.verify(repositoryMock).deleteById(Mockito.any());
	}

}
