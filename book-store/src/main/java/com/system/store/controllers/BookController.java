package com.system.store.controllers;

import com.system.store.dtos.BookDto;
import com.system.store.models.Author;
import com.system.store.models.Book;
import com.system.store.repositories.AuthorRepository;
import com.system.store.repositories.BookRepository;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<BookDto> add(@RequestBody Book book) {
        Optional<Author> optional = authorRepository.findById(book.getAuthor().getId());
        if (optional.isPresent()) {
            book.setAuthor(optional.get());
            repository.save(book);
            return ResponseEntity.ok(new BookDto(book));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> find(@PathVariable ObjectId id) {
        Optional<Book> optional = repository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new BookDto(optional.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Collection<BookDto>> listAll() {
        return ResponseEntity.ok(BookDto.toList(repository.findAll()));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@RequestBody Book book, @PathVariable ObjectId id) {
        if (book.getAuthor() != null) {
            Optional<Author> authorOptional = authorRepository.findById(book.getAuthor().getId());
            if (authorOptional.isPresent()) {
                book.setAuthor(authorOptional.get());
            }
        }

        Optional<Book> optional = repository.findById(id);
        if (optional.isPresent()) {
            Book bookSaved = optional.get();
            bookSaved.setIsbn(book.getIsbn());
            bookSaved.setTitle(book.getTitle());
            bookSaved.setAuthor(book.getAuthor());
            repository.save(bookSaved);
            return ResponseEntity.ok(new BookDto(bookSaved));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<Book> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.delete(optional.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
