package com.system.store.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.system.store.models.User;
import com.system.store.repositories.UserRepository;
import com.system.store.templates.UserTemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserDetailsServiceImpl service;

	@BeforeEach
	public void setup() {
		FixtureFactoryLoader.loadTemplates("com.system.store.templates");
	}

	@Test
	public void shouldFindUserByEmail() {
		User user = Fixture.from(User.class).gimme(UserTemplate.MOCK);
		String email = "email@email.com";

		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

		UserDetails userDetails = service.loadUserByUsername(email);

		Assert.assertNotNull(userDetails);
	}

	@Test
	public void shouldThrowExceptionWhenUserNotFound() {
		String email = "email@email.com";

		Mockito.when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(email));
	}
}
