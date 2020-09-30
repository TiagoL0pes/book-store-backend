package com.system.store.repositories;

import com.system.store.models.Author;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, ObjectId> {

    Author findByName(String name);
}
