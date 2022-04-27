package com.angermannalaget.angermann;

import com.angermannalaget.angermann.auth.Role;
import com.angermannalaget.angermann.auth.User;
import com.angermannalaget.angermann.auth.UserService;
import com.angermannalaget.angermann.model.Category;
import com.angermannalaget.angermann.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CMDL{ /*implements CommandLineRunner {
        private static final Logger logger = LoggerFactory.getLogger(CMDL.class);

        private UserService userDetailsService;
        private SongService songService;

    public CMDL(UserService userDetailsService, SongService songService) {
            this.userDetailsService = userDetailsService;
            this.songService = songService;
        }

        @Override
        public void run (String...args) throws Exception {
            logger.info("CMD-RUNNING HOT");
            userDetailsService.addUser(new User("bs", "8558"));
            userDetailsService.addRole(new Role("bs", "ADMIN"));
            userDetailsService.addUser(new User("ds", "8558"));
            userDetailsService.addRole(new Role("ds", "USER"));

           // Adding categories
            songService.addCategories(new Category("A-laget"));
            songService.addCategories(new Category("Ypsilon"));
            songService.addCategories(new Category("Birkarlarna"));
            songService.addCategories(new Category("Vajan"));
            songService.addCategories(new Category("Härjajämt"));
            songService.addCategories(new Category("Jamtamot"));
            songService.addCategories(new Category("Skvadern"));
            songService.addCategories(new Category("Övriga"));

    }*/
}
