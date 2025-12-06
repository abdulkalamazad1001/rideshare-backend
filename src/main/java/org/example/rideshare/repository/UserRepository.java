package org.example.rideshare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.example.rideshare.model.User;

public interface UserRepository  extends MongoRepository<User,String> {
    User findByUsername(String username);
}