package com.dronworks.jpastudy.commandrunner;

import com.dronworks.jpastudy.entity.User;
import com.dronworks.jpastudy.service.UserDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDAOServiceCommandLineRunner implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private UserDAOService userDAOService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Andrey", "Admin");
        long id = userDAOService.insert(user);
        log.info("user created: {} ", id);
    }
}
