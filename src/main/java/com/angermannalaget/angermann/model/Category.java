package com.angermannalaget.angermann.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

@Data
@Document
public class Category {
    @Id String id;

    private String category;

    public Category(String category) {
        this.category = category;
    }
}

