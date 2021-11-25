package api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import savehandler.UserSaveHandler;
import user.User;

/**
 * Service for the webserver.
 * Contains methods for what is should happen when controller get request.
 */

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

  /**
   * Method that creates a default userlist.
   * Checks if a userlist is existing, if not makes one.
   *
   * @return return a userlist, could be empty.
   */

  public static List<User> createDefaultList() {
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

  /**
  * Method that save a userlist.
  * Overwrites an existing userlist to the a file.
  *
  */

  public void autosaveUserList() {
    try {
      userSaveHandler.updateFile(userList);
    } catch (IllegalStateException e) {
      System.out.println("Could not autosave" + e);
    }
  }

  /**
   * Methods that gets a user based off of the username.
   * Goes through the userlist and checks the usernames.
   *
   * @param username the username of the user.
   * @return the user that you want to get.
   */

  public User getUser(String username) {
    for (User user : Objects.requireNonNull(getUserList())) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Creates a user in the userlist.
   * Goes through list to see if user already exist.
   *
   * @param user the user you want to be added.
   */


  public void createUser(User user) {
    if (userList.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
      throw new IllegalArgumentException("Username is taken");
    }
    userList.add(0, user);
    userSaveHandler.updateFile(userList);
  }

  /**
   * Updates a user, used for updating balance.
   * Removes the user with the same username,
   * Adds the user back but with a different balance.
   *
   * @param user the user that is being updated.
   */

  public void updateUser(User user) {
    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getUsername().equals(user.getUsername())) {
        userList.remove(userList.get(i));
        userList.add(0, user);
      }
    }
    autosaveUserList();
  }

  /**
   * Deletes a user from the userlist.
   * Finds the right username and removes the user.
   *
   * @param username is the username of the user you want to remove.
   */

  public void deleteUser(String username) {
    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getUsername().equals(username)) {
        userList.remove(i);
        autosaveUserList();
        return;
      }
    }
  }
}