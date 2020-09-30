package com.system.store.controllers;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.system.store.dtos.AuthorDto;
import com.system.store.models.Author;
import com.system.store.models.User;
import com.system.store.repositories.AuthorRepository;
import com.system.store.templates.AuthorTemplate;
import com.system.store.templates.UserTemplate;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorController controller;

    @BeforeEach
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.system.store.templates");
    }

    @Test
    public void shouldSaveNewAuthor() {
        Author author = Fixture.from(Author.class).gimme(AuthorTemplate.MOCK);

        Mockito.when(repository.save(any())).thenReturn(author);

        ResponseEntity<AuthorDto> responseEntity = controller.add(author);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldFindAuthorById() {
        ObjectId id = new ObjectId();
        Author author = Fixture.from(Author.class).gimme(AuthorTemplate.MOCK);

        Mockito.when(repository.findById(any())).thenReturn(Optional.of(author));

        ResponseEntity<AuthorDto> responseEntity = controller.find(id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldListAllAuthors() {
        List<Author> authors = Fixture.from(Author.class).gimme(1, AuthorTemplate.MOCK);

        Mockito.when(repository.findAll()).thenReturn(authors);

        ResponseEntity<Collection<AuthorDto>> responseEntity = controller.listAll();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldUpdateExistingAuthor() {
        ObjectId id = new ObjectId();
        Author author = Fixture.from(Author.class).gimme(AuthorTemplate.MOCK);
        author.setName("Verônica Roth");

        Mockito.when(repository.findById(any())).thenReturn(Optional.of(author));
        Mockito.when(repository.save(any())).thenReturn(author);

        ResponseEntity<AuthorDto> responseEntity = controller.update(author, id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(author.getName(), responseEntity.getBody().getName());
    }

    @Test
    public void shouldDeleteExistingAuthor() {
        ObjectId id = new ObjectId();
        Author author = Fixture.from(Author.class).gimme(AuthorTemplate.MOCK);

        Mockito.when(repository.findById(any())).thenReturn(Optional.of(author));
        Mockito.doNothing().when(repository).delete(any());

        ResponseEntity responseEntity = controller.delete(id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
