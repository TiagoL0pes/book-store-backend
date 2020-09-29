package com.system.store.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.system.store.dtos.UserDto;
import com.system.store.models.User;
import com.system.store.repositories.UserRepository;
import com.system.store.templates.UserTemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserController controller;

	@BeforeEach
	public void setup() {
		FixtureFactoryLoader.loadTemplates("com.system.store.templates");
	}

	@Test
	public void shouldSaveNewUser() {
		User user = Fixture.from(User.class).gimme(UserTemplate.MOCK);

		Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
		Mockito.when(repository.save(any())).thenReturn(user);

		ResponseEntity<UserDto> responseEntity = controller.add(user);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
	}

	@Test
	public void shouldNotSaveUserAlreadyRegistered() {
		User user = Fixture.from(User.class).gimme(UserTemplate.MOCK);

		Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

		ResponseEntity<UserDto> responseEntity = controller.add(user);

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void shouldFindUserById() {
		ObjectId id = new ObjectId();
		User user = Fixture.from(User.class).gimme(UserTemplate.MOCK);

		Mockito.when(repository.findById(any())).thenReturn(Optional.of(user));

		ResponseEntity<UserDto> responseEntity = controller.find(id);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
	}

	@Test
	public void shouldListAllUsers() {
		List<User> users = Fixture.from(User.class).gimme(1, UserTemplate.MOCK);

		Mockito.when(repository.findAll()).thenReturn(users);

		ResponseEntity<Collection<UserDto>> responseEntity = controller.listAll();

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
	}

	@Test
	public void shouldUpdateExistingUser() {
		ObjectId id = new ObjectId();
		User user = Fixture.from(User.class).gimme(UserTemplate.MOCK);
		user.setPassword(new BCryptPasswordEncoder().encode("12345678"));

		Mockito.when(repository.findById(any())).thenReturn(Optional.of(user));
		Mockito.when(repository.save(any())).thenReturn(user);

		ResponseEntity<UserDto> responseEntity = controller.update(user, id);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertEquals(user.getPassword(), responseEntity.getBody().getPassword());
	}

	@Test
	public void shouldDeleteExistingUser() {
		ObjectId id = new ObjectId();
		User user = Fixture.from(User.class).gimme(UserTemplate.MOCK);

		Mockito.when(repository.findById(any())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(repository).delete(any());

		ResponseEntity responseEntity = controller.delete(id);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
