package it1901.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import savehandler.*;
import user.User;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {


    @GetMapping("/users")
    public List<User> getUserList() throws IOException {
        return UserSaveHandler.getUserList();
    }

    @GetMapping("/users/{username}")
    public User getUser(@PathVariable String username) throws IOException{
        return UserSaveHandler.getUser(username);
    }

    @PostMapping("/users/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser (@RequestBody User newUser) throws IOException {
        UserSaveHandler.createUser(newUser);
        return newUser;
    }

    @PostMapping("/users/set-active")
    public User activeUser(@RequestBody User newUser) throws IOException {
        UserSaveHandler.setActive(newUser);
        return newUser;
    }
}