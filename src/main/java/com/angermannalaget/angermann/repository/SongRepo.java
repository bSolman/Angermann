package com.angermannalaget.angermann.repository;

import com.angermannalaget.angermann.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepo extends MongoRepository<Song, String> {

    Song getSongByName(String name);

    void deleteById(String id);

    Optional<Song> getSongById(String id);

    List<Song> getSongsBySongFor(String songFor);

}
