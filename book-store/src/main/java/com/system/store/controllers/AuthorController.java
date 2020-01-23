package com.system.store.controllers;

import com.system.store.dtos.AuthorDto;
import com.system.store.models.Author;
import com.system.store.repositories.AuthorRepository;
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
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<AuthorDto> add(@RequestBody Author author) {
        repository.save(author);
        return ResponseEntity.ok(new AuthorDto(author));
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDto> find(@PathVariable ObjectId id) {
        Optional<Author> optional = repository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new AuthorDto(optional.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/list")
    public ResponseEntity<Collection<AuthorDto>> listAll() {
        return ResponseEntity.ok(AuthorDto.toList(repository.findAll()));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorDto> update(@RequestBody Author author, @PathVariable ObjectId id) {
        Optional<Author> optional = repository.findById(id);
        if (optional.isPresent()) {
            Author authorSaved = optional.get();
            authorSaved.setName(author.getName());
            authorSaved.setEmail(author.getEmail());
            repository.save(authorSaved);
            return ResponseEntity.ok(new AuthorDto(authorSaved));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<Author> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.delete(optional.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
