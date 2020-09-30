package com.system.store.controllers;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.system.store.dtos.BookDto;
import com.system.store.models.Author;
import com.system.store.models.Book;
import com.system.store.repositories.AuthorRepository;
import com.system.store.repositories.BookRepository;
import com.system.store.templates.AuthorTemplate;
import com.system.store.templates.BookTemplate;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setup() {
        FixtureFactoryLoader.loadTemplates("com.system.store.templates");
    }

    @Test
    public void shouldSaveNewBook() {
        Author author = Fixture.from(Author.class).gimme(AuthorTemplate.MOCK);
        Book book = Fixture.from(Book.class).gimme(BookTemplate.MOCK);

        Mockito.when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.save(any())).thenReturn(book);

        ResponseEntity<BookDto> responseEntity = bookController.add(book);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldFindBookById() {
        ObjectId id = new ObjectId();
        HashMap<String, String> headers = new HashMap<>();
        Book book = Fixture.from(Book.class).gimme(BookTemplate.MOCK);

        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        ResponseEntity<BookDto> responseEntity = bookController.find(id, headers);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldListAllBooks() {
        List<Book> books = Fixture.from(Book.class).gimme(1, BookTemplate.MOCK);

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        ResponseEntity<Collection<BookDto>> responseEntity = bookController.listAll();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void shouldUpdateExistingBook() {
        ObjectId id = new ObjectId();
        Author author = Fixture.from(Author.class).gimme(AuthorTemplate.MOCK);
        Book book = Fixture.from(Book.class).gimme(BookTemplate.MOCK);
        book.setTitle("Divergent");

        Mockito.when(authorRepository.findById(any())).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(any())).thenReturn(book);

        ResponseEntity<BookDto> responseEntity = bookController.update(book, id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(book.getTitle(), responseEntity.getBody().getTitle());
    }

    @Test
    public void shouldDeleteExistingAuthor() {
        ObjectId id = new ObjectId();
        Book book = Fixture.from(Book.class).gimme(BookTemplate.MOCK);

        Mockito.when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        Mockito.doNothing().when(bookRepository).delete(any());

        ResponseEntity responseEntity = bookController.delete(id);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
