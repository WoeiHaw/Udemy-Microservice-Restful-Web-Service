package com.in28minutes.rest.webservices.restful_web_services.user;

import com.in28minutes.rest.webservices.UserNotFoundException;
import com.in28minutes.rest.webservices.restful_web_services.UserDaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        User user =userDaoService.findOne(userId);
        if(user ==  null){
            throw new UserNotFoundException("id:"+userId);
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
         return ResponseEntity.created(location).build();
    }
}
