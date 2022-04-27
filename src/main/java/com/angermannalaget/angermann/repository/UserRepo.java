package com.angermannalaget.angermann.repository;

import com.angermannalaget.angermann.auth.User;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    User findByUserName(String userName);
    void deleteById(@NonNull String id);
    boolean existsByUserName(String userName);
}
