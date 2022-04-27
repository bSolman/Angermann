package com.angermannalaget.angermann.model;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "song")
public class Song {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String author;

    private List<String> song;

    private String songFor;

    private String harmony;

    public Song(String name, String author, List<String> song, String songFor, String harmony) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.song = song;
        this.songFor = songFor;
        this.harmony = harmony;
    }

}
