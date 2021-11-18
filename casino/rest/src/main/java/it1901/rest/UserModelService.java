package it1901.rest;

import org.springframework.stereotype.Service;
import savehandler.UserSaveHandler;
import user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserModelService {

    private List<User> userList;
    private final UserSaveHandler userSaveHandler;

    public UserModelService(List<User> userList) {
        this.userList = userList;
        this.userSaveHandler = new UserSaveHandler();
    }

    public UserModelService() {
        this(createDefaultList());
    }


    private static List<User> createDefaultList() {
        List<User> userList = new ArrayList<>();
        UserSaveHandler userSaveHandler = new UserSaveHandler();
        if (userSaveHandler.getUserList() != null) {
            userList = userSaveHandler.getUserList();
            return userList;
        }
        return userList;
    }


    public List<User> getUserList() {
        return userSaveHandler.getUserList();
    }


    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


    public void autosaveUserList() {
            try {
                userSaveHandler.updateFile(userList);
                System.out.println(userList.toString());
            } catch (IllegalStateException e) {
                System.out.println("Could not autosave" + e);
            }
        }

    public User getUser(String username) {
        for (User user : Objects.requireNonNull(getUserList())) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void createUser(User user) {
        userSaveHandler.createUser(user);
    }

    public void updateUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(user.getUsername())) {
                userList.remove(userList.get(i));
                userList.add(0, user);
            }
        }
        autosaveUserList();
    }
}
