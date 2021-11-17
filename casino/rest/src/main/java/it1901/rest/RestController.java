package it1901.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import savehandler.*;
import user.User;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserSaveHandler userSaveHandler = new UserSaveHandler();


    @GetMapping("/users")
    public List<User> getUserList() throws IOException {
        return userSaveHandler.getUserList();
    }


    @GetMapping("/users/{Username}")
    public User getUser(@PathVariable("Username") String Username) throws IOException {
        return userSaveHandler.getUser(Username);
    }


    @PostMapping("/users/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser (@RequestBody User newUser) throws IOException {
        userSaveHandler.createUser(newUser);
    }

    @PostMapping("/users/set-active")
    public void  activeUser(@RequestBody User newUser) throws IOException {
        userSaveHandler.setActive(newUser);
    }


}