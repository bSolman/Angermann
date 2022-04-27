package com.angermannalaget.angermann.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "song")
public class Songs {

    private String harmony;

    private String name;

    private String author;

    private String songFor;

    private String theSong;


}
