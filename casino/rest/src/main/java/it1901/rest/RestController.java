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

    private UserSaveHandler userSaveHandler = new UserSaveHandler();

    @Autowired
    private UserModelService userModelService = new UserModelService(userSaveHandler.getUserList());


    private void autosaveUserList(){
        userModelService.autosaveUserList();
    }


    @GetMapping("/users")
    public List<User> getUserList() {
        return userModelService.getUserList();
    }


    @GetMapping("/users/{Username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User getUser(@PathVariable("Username") String Username){
        return userModelService.getUser(Username);
    }


    @PostMapping("/users/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser (@RequestBody User newUser){
        userModelService.createUser(newUser);
    }

    @PostMapping("/users/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@RequestBody User user){
        userModelService.updateUser(user);
    }

    @DeleteMapping("/delete/{Username}")
    public boolean removeUser(@PathVariable ("Username") String username) {
        userModelService.deleteUser(username);
        return true;
    }
}

