package com.angermannalaget.angermann.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.angermannalaget.angermann.auth.Role;
import com.angermannalaget.angermann.auth.User;
import com.angermannalaget.angermann.auth.UserService;
import com.angermannalaget.angermann.model.Song;
import com.angermannalaget.angermann.model.Songs;
import com.angermannalaget.angermann.service.SongService;
import com.angermannalaget.angermann.util.DocReader;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class SongUploadController {

    private static final Logger logger = LoggerFactory.getLogger(SongUploadController.class);

    private final SongService songService;
    private final UserService userService;

    @Autowired
    public SongUploadController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }


    @GetMapping("/landingPage")
    public String mainPage(Model model){
        model.addAttribute("songList", songService.getSongsOnly());
        model.addAttribute("categories", songService.getCategories());
        return "landingPage";
    }

    @GetMapping("/uploadSong")
    public String uploadFile(Model model){
        model.addAttribute("songFor", songService.getCategories());
        return "uploadSong";
    }

    @GetMapping("/categoryContent")
    public String categoryContent(@RequestParam(value = "id") String category, Model model){
        model.addAttribute("songs", songService.getSongBy(category));
        return "displaySongList";
    }

    @GetMapping("/getSong")
    public String getSong(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("songContent", songService.getSongByName(name));
        return "displaySongContent";
    }

    @RequestMapping("/getSongByName")
    public String getSongByName(@RequestParam(value = "name") String name, Model model){
        logger.info("INPUT NAME: " + name);
        model.addAttribute("songContent", songService.getSongByName(name));

        return "displaySongContent";
    }

    @RequestMapping("/editChoiceSong")
    public String chooseSongToEdit(Model model){
        model.addAttribute("songs", songService.getAllSongs());
        return "editChoiceSong";
    }

    @RequestMapping("/editSong")
    public String editSong(@RequestParam(value = "songName") String songName, Model model){
        model.addAttribute("songContent", songService.getSongByName(songName));
        model.addAttribute("songFor", songService.getCategories());
        return "editSong";
    }

    @RequestMapping("/updateSong")
    public String updateSong(@ModelAttribute("songs") Songs songs, RedirectAttributes attributes){
        if (!songService.songExist(songs.getName())){
            attributes.addFlashAttribute("message", "Hoppsan något gick åt helvete. Sången finns inte. Pröva igen?");
            return "redirect:/editChoiceSong";
        }
        songService.updateSong(songs);
        return "redirect:/editChoiceSong";
    }

    @RequestMapping("manageUserView")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String manageUserView(Model model){
        model.addAttribute("roles", userService.getAllRoles());
        return "manageUserView";
    }

    @RequestMapping("/removeUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String removeUser(@RequestParam(value = "roleId") String roleId, RedirectAttributes attributes){
        if (userService.existsByRoleId(roleId)){
            Role role = userService.getRoleById(roleId);
            userService.deleteUserById(userService.getUserByName(role.getUsername()).getId());
            userService.deleteRoleById(roleId);
            attributes.addFlashAttribute("message", "Användaren " + role.getUsername() + " Har tagits bort från systemet");
            return "redirect:/manageUserView";
        }
        attributes.addFlashAttribute("message", "FEL! Användaren kan inte tas bort. Konsultera konsulterna eller webansvarig");
        return "redirect:/manageUserView";
    }

    @RequestMapping("/addUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUser(@ModelAttribute User user, RedirectAttributes attributes){
        if (userService.existsByUserName(user.getUserName())){
            attributes.addFlashAttribute("message", "FEL! Användaren " + user.getUserName() +  " är redan inlagd i systemet");
            return "redirect:/manageUserView";
        }

        userService.addUser(user);
        userService.addRole(new Role(user.getUserName(), "USER"));
        attributes.addFlashAttribute("message", "Användaren" + user.getUserName() +  " är nu inlagd i systemet");

        return  "redirect:/manageUserView";
    }

    @RequestMapping("/alterRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String alterRole(@RequestParam(value = "alterRole") String id, @RequestParam(value = "role") String role,RedirectAttributes attributes){
        if (userService.existsByRoleId(id)){
            userService.alterRole(id, role);
            attributes.addFlashAttribute("message", userService.getRoleById(id).getUsername() +  " Har fått rollen " + userService.getRoleById(id).getRole());
            return  "redirect:/manageUserView";
        }
        attributes.addFlashAttribute("message", "FEL! Ingen roll kunde uppdateras");
        return  "redirect:/manageUserView";
    }

    @RequestMapping("removeSongs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String removeSongs(Model model){
        model.addAttribute("songs", songService.getAllSongs());
        return "removeSongs";
    }

    @RequestMapping("/removeSong")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String removeSong (@RequestParam(value = "songId") String id, RedirectAttributes attributes){
        if (songService.removeSong(id)){
            attributes.addFlashAttribute("message", "Song removed!");
        }
        else {
            attributes.addFlashAttribute("message", "Error no such song found!");
        }
        return "redirect:/removeSongs";
    }

    @RequestMapping("/upload")
    public String uploadDoc(@RequestParam("song") MultipartFile song, @ModelAttribute("songs") Songs songs, RedirectAttributes attributes){
        if (song.isEmpty() && songs.getTheSong().isEmpty()) {
            //Skickar ett glatt meddelande till webläsaren(thymeleaf)
            attributes.addFlashAttribute("message", "Pls select a file to upload.");
            return "redirect:/uploadSong";
        }
        if (songService.songExist(songs.getName())){
            attributes.addFlashAttribute("message", "Songnamnet finns redan i systemet. Välj ett nytt namn på sången");
            attributes.addFlashAttribute("song", songs.getTheSong());
            logger.info("DUBBELT NAMN: " + songs.getName());
            return "redirect:/uploadSong";
        }

        DocReader docReader = new DocReader();
        List<String> songContent = new ArrayList<>();

        if (!songs.getTheSong().isEmpty()){
            songContent = docReader.splitTextField(songs.getTheSong());
            attributes.addFlashAttribute("message", "File upload successful");
        }

        if (getExtension(song.getOriginalFilename()).equals("txt")){
            songContent = docReader.parseTxt(song);
            attributes.addFlashAttribute("message", "File upload successful");
        }
        else if (getExtension(song.getOriginalFilename()).equals("docx")){
            songContent = docReader.parseDocX(song);
            attributes.addFlashAttribute("message", "File upload successful");
        }
        else {
            System.out.println("FUCK");
        }

        songService.insertSong(new Song(songs.getName(), songs.getAuthor(), songContent, songs.getSongFor(), songs.getHarmony()));

        return "redirect:/uploadSong";
    }

    public String getExtension(String fileName){
        return Files.getFileExtension(fileName);
    }

}
