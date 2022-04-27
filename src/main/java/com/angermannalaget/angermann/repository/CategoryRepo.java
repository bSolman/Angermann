package com.angermannalaget.angermann.repository;

import com.angermannalaget.angermann.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {
    boolean existsByCategory(String category);
}
