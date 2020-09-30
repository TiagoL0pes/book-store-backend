package com.system.store.repositories;

import com.system.store.filters.BookFilter;
import com.system.store.models.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    Book findByTitle(String title);

    @Query("{title : :#{#filter.title}}")
    Book findByTitle(@Param("filter") BookFilter filter);
}
