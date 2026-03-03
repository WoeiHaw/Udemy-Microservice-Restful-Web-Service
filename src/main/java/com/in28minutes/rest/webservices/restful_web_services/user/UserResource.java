package com.in28minutes.rest.webservices.restful_web_services.user;

import com.in28minutes.rest.webservices.restful_web_services.UserDaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResource {

    private final UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{userId}")
    public User retrieveUsers(@PathVariable Integer userId) {
        return userDaoService.findOne(userId);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user){
        userDaoService.save(user);
    }
}
