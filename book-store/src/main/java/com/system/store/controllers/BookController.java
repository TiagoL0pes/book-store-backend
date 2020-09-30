package com.system.store.controllers;

import com.system.store.dtos.BookDto;
import com.system.store.helpers.NullAllowed;
import com.system.store.models.Author;
import com.system.store.models.Book;
import com.system.store.repositories.AuthorRepository;
import com.system.store.repositories.BookRepository;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<BookDto> add(@RequestBody @Validated(NullAllowed.class) Book book) {
        Optional<Author> optional = authorRepository.findById(book.getAuthor().getId());
        if (optional.isPresent()) {
            book.setAuthor(optional.get());
            bookRepository.save(book);
            return ResponseEntity.ok(new BookDto(book));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> find(@PathVariable ObjectId id, @RequestHeader Map<String, String> headers) {
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new BookDto(optional.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Collection<BookDto>> listAll() {
        return ResponseEntity.ok(BookDto.toList(bookRepository.findAll()));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@RequestBody Book book, @PathVariable ObjectId id) {
        if (book.getAuthor() != null) {
            Optional<Author> authorOptional = authorRepository.findById(book.getAuthor().getId());
            authorOptional.ifPresent(book::setAuthor);
        }

        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isPresent()) {
            Book bookSaved = optional.get();
            bookSaved.setIsbn(book.getIsbn());
            bookSaved.setTitle(book.getTitle());
            bookSaved.setAuthor(book.getAuthor());
            bookRepository.save(bookSaved);
            return ResponseEntity.ok(new BookDto(bookSaved));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isPresent()) {
            bookRepository.delete(optional.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
