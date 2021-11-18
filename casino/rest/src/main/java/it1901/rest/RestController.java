package it1901.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import savehandler.*;
import user.User;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private final UserModelService userModelService = new UserModelService();

    private void autosaveUserList(){
        userModelService.autosaveUserList();
    }


    @GetMapping("/users")
    public List<User> getUserList() {
        return userModelService.getUserList();
    }


    @GetMapping("/users/{Username}")
    public User getUser(@PathVariable("Username") String Username){
        return userModelService.getUser(Username);
    }


    @PostMapping("/users/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser (@RequestBody User newUser){
        userModelService.createUser(newUser);
    }

    @PostMapping("/users/{Username}/update")
    public void updateUser(@PathVariable("Username") String Username){
        userModelService.updateUser(getUser(Username));
        System.out.println(getUser(Username));
        autosaveUserList();
    }
}