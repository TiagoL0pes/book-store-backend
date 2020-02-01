package com.system.store.repositories;

import com.system.store.models.User;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    public Optional<User> findByEmail(String email);

}
