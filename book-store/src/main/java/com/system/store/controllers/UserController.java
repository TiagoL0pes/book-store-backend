package com.system.store.controllers;

import com.system.store.dtos.UserDto;
import com.system.store.models.User;
import com.system.store.repositories.UserRepository;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody User user) {
        repository.save(user);
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> find(@PathVariable ObjectId id) {
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(new UserDto(optional.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Collection<UserDto>> listAll() {
        return ResponseEntity.ok(UserDto.toList(repository.findAll()));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@RequestBody User user, @PathVariable ObjectId id) {
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            User userSaved = optional.get();
            userSaved.setEmail(user.getEmail());
            userSaved.setPassword(user.getPassword());
            repository.save(userSaved);
            return ResponseEntity.ok(new UserDto(userSaved));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<User> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.delete(optional.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
