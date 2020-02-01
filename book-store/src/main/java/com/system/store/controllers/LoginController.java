package com.system.store.controllers;

import com.system.store.dtos.UserDto;
import com.system.store.forms.LoginForm;
import com.system.store.models.User;
import com.system.store.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody LoginForm form) {
        Optional<User> op = repository.findByEmailAndPassword(form.getEmail(), form.getPassword());

        if (op.isPresent()) {
            return ResponseEntity.ok(new UserDto(op.get()));
        }

        return ResponseEntity.notFound().build();
    }
}
