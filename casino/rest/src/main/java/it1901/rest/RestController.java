package it1901.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import savehandler.UserSaveHandler;
import user.User;

import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/users")
    public List<User> getUserList() throws IOException {
        return UserSaveHandler.getUserList();
    }

    @PostMapping("/users/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser (@RequestBody User newUser) throws IOException {
        UserSaveHandler.createUser(newUser);
        return newUser;
    }
}
