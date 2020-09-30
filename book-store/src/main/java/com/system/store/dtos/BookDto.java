package com.system.store.dtos;

import com.system.store.models.Book;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class BookDto {

    private String id;
    private String isbn;
    private String title;
    private AuthorDto author;

    public BookDto(Book book) {
        id = book.getId().toHexString();
        isbn = book.getIsbn();
        title = book.getTitle();

        if (book.getAuthor() != null) {
            author = new AuthorDto(book.getAuthor());
        }
    }

    public static Collection<BookDto> toList(Collection<Book> collection) {
        return collection
                .stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }
}
