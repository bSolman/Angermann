package com.angermannalaget.angermann.service;

import com.angermannalaget.angermann.model.Category;
import com.angermannalaget.angermann.model.Song;
import com.angermannalaget.angermann.model.Songs;
import com.angermannalaget.angermann.repository.CategoryRepo;
import com.angermannalaget.angermann.repository.SongRepo;
import com.angermannalaget.angermann.util.DocReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SongService {

    private static final Logger logger = LoggerFactory.getLogger(SongService.class);
    private final SongRepo songRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public SongService(SongRepo songRepo, CategoryRepo categoryRepo) {
        this.songRepo = songRepo;
        this.categoryRepo = categoryRepo;
    }

    public void addCategories(@RequestBody Category category){
        categoryRepo.save(category);
    }

    public List<Category> getCategories(){
        return categoryRepo.findAll();
    }

    public boolean removeSong(String songId){
        try {
            if (songRepo.getSongById(songId) == null){
                return false;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        songRepo.deleteById(songId);
        return true;
    }

    public List<Song> getAllSongs(){
        return songRepo.findAll();
    }

    public String[] getSongsOnly(){
        return songRepo.findAll().
                stream().
                flatMap(x -> Stream.of(x.getName())).
                toArray(size -> new String[size]);
    }

    public void insertSong(@RequestBody Song song){
        songRepo.save(song);
    }

    public List<Song> getSongBy(String songFor){
        return songRepo.getSongsBySongFor(songFor);
    }

    public Song getSongByName(String name){
        return songRepo.getSongByName(name);
    }

    public boolean songExist(String songName){
        try {
            if (songRepo.getSongByName(songName) == null){
                return false;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        return songRepo.getSongByName(songName).getName().equals(songName);
    }

    public void updateSong(Songs songs) {
        Song s = songRepo.getSongByName(songs.getName());
        Optional<Song> currentSong = songRepo.getSongById(s.getId());
        if (currentSong.isPresent()){
            Song x = currentSong.get();
            DocReader dr = new DocReader();
            x.setSong(dr.splitTextField(songs.getTheSong()));
            x.setSongFor(songs.getSongFor());
            x.setHarmony(songs.getHarmony());
            x.setName(songs.getName());
            songRepo.save(x);
        }
        else {
            logger.info("FUCK");
        }

    }

    public boolean categoryExists(String category){
        return categoryRepo.existsByCategory(category);
    }
}
