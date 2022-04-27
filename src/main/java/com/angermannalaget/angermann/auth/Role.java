package com.angermannalaget.angermann.auth;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "role")
public class Role {
    @Id
    private String id;

    private String username;

    private String role;

    public Role(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
