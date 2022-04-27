package com.angermannalaget.angermann.controller;

import com.angermannalaget.angermann.auth.User;
import com.angermannalaget.angermann.model.Category;
import com.angermannalaget.angermann.model.Song;
import com.angermannalaget.angermann.repository.CategoryRepo;
import com.angermannalaget.angermann.repository.SongRepo;
import com.angermannalaget.angermann.repository.UserRepo;
import com.angermannalaget.angermann.service.SongService;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SongController {

    private final SongRepo songRepo;
    private final SongService songService;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;

    public SongController(UserRepo userRepo, SongRepo songRepo, SongService songService, CategoryRepo categoryRepo) {
        this.songRepo = songRepo;
        this.songService = songService;
        this.categoryRepo = categoryRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/addUser")
    public List<User> addUser(@RequestBody List<User> user){
        return userRepo.saveAll(user);
    }

    @PostMapping("/addSong")
    public List<Song> addSongs(@RequestBody List<Song> songs){
        return songRepo.saveAll(songs);
    }

    @PostMapping("/addCategory")
    public List<Category> addCategory(@RequestBody List<Category> categories){
        return categoryRepo.saveAll(categories);
    }

    @GetMapping("/songFor")
    public List<Song> getSongFor(){
        return songRepo.getSongsBySongFor("skvadern");
    }

    @GetMapping("/all")
    public List<Song> getAllSongs(){
        return songRepo.findAll();
    }
}
