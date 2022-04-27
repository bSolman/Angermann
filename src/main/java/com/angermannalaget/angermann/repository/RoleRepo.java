package com.angermannalaget.angermann.repository;

import com.angermannalaget.angermann.auth.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, String>{
    List<Role> findByUsername(String username);
    Optional<Role> findById(String id);
    void deleteById(String id);
    boolean existsById(String id);
}
