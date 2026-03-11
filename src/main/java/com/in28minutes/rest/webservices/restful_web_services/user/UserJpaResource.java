package com.in28minutes.rest.webservices.restful_web_services.user;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {


    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> retrieveUsers(@PathVariable Integer userId) {
        User user =userRepository.findById(userId).orElse(null);
        if(user ==  null){
            throw new UserNotFoundException("id:"+userId);
        }
        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder  link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser( @Valid @RequestBody User user){
       User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
         return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userRepository.deleteById(userId);

    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostForUsers(@PathVariable Integer id) {
        User user =userRepository.findById(id).orElse(null);
        if(user ==  null){
            throw new UserNotFoundException("id:"+id);
        }
        return user.getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable Integer id,@Valid @RequestBody Post post) {
        User user =userRepository.findById(id).orElse(null);
        if(user ==  null){
            throw new UserNotFoundException("id:"+id);
        }
       post.setUser(user);
       Post savePost =  postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savePost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
