package com.dronworks.jpastudy.commandrunner;

import com.dronworks.jpastudy.entity.User;
import com.dronworks.jpastudy.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryCommandLineRunner implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Jill", "Admin");
        userRepository.save(user);
        Optional<User> byId = userRepository.findById(1L);
        log.info("User with id {} retrieved {}", 1, byId);

        List<User> all = userRepository.findAll();
        log.info("Users retrieved {}", all);

        log.info("user created: {} ", user.getId());
    }
}
