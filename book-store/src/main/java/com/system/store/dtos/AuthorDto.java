package com.system.store.dtos;

import com.system.store.models.Author;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class AuthorDto {

    private String id;
    private String name;
    private String email;

    public AuthorDto(Author author) {
        this.id = author.getId().toHexString();
        this.name = author.getName();
        this.email = author.getEmail();
    }

    public static Collection<AuthorDto> toList(Collection<Author> collection) {
        return collection
                .stream()
                .map(AuthorDto::new)
                .collect(Collectors.toList());
    }
}
