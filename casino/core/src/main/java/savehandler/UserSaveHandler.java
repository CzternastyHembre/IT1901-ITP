package savehandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import user.User;


/**
 * Class for saving the users objects.
 */
public class UserSaveHandler {
  /**
   * Variable SAVE_FILE is saved as the path to the saving destination.
   */
  private final Path saveFile;

  public UserSaveHandler() {
    this(false);
  }


  /**
   * Constructor for UserSaveHandler.
   * The savehandler will save the files in different folder if used in a test.
   *
   * @param isTest a boolean for checking if it is used for a test or not.
   */

  public UserSaveHandler(boolean isTest) {
    if (isTest) {
      this.saveFile = Paths.get(System.getProperty("user.home"), "TestData", "users.json");
    } else {
      this.saveFile = Paths.get(System.getProperty("user.home"), "CasinoData", "users.json");
    }
    createDirectory();
  }


  /**
   * Method that creates a directory based off of SaveFile path.
   *
   */

  public void createDirectory() {
    String path = String.valueOf(saveFile);
    path = path.replaceAll("users.json", "");
    if (Files.exists(Path.of(path))) {
      return;
    }
    try {
      Files.createDirectory(Path.of(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Gets the existing userList and add a user to this. Updates the file with the new user.
   *
   * @param user the user that is being created.
   */

  public void createUser(User user) {
    List<User> userList = getUserList();
    if (userList.stream().anyMatch(u -> u.getUsername()
            .equals(user.getUsername()))) {
      throw new IllegalArgumentException("Username is taken");
    }
    userList.add(0, user);
    updateFile(userList);
  }

  /**
   * Updates the file with a new userList. Overwrites the existing file.
   *
   * @param userList takes in the new userList and overwrites.
   */

  public void updateFile(List<User> userList) {
    try (FileWriter fileWriter = new FileWriter(String.valueOf(saveFile),
            StandardCharsets.UTF_8)) {
      Gson gson = new Gson();
      String jsonSaveString = gson.toJson(userList);
      fileWriter.append(jsonSaveString);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the json file is an empty file.
   *
   * @return true if the file has the length of 0.
   */

  public boolean isEmpty() {
    File file = new File(String.valueOf(saveFile));
    return file.length() == 0;
  }

  /**
   * Reads the file SAVE_FILE and return an arrayList of objects user.
   *
   * @return userList is the arrayList of the object user.
   */

  public List<User> getUserList() {
    if (isEmpty()) {
      createDirectory();
      try (FileWriter fileWriter = new FileWriter(String.valueOf(saveFile),
              StandardCharsets.UTF_8)) {
        fileWriter.write("");
        fileWriter.close();
        return new ArrayList<>();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try (Scanner sc = new Scanner(new File(String.valueOf(saveFile)), StandardCharsets.UTF_8)) {
      String userString = sc.nextLine();
      Gson gson = new Gson();
      ArrayList<User> userList;
      Type userListType = new TypeToken<ArrayList<User>>() {
      }.getType();
      userList = gson.fromJson(userString, userListType);
      return userList;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Checks if there exist a user with the given username.
   *
   * @param username the username that is being checked.
   * @return null or the object user with the username.
   */

  public User getUser(String username) {

    for (User user : Objects.requireNonNull(getUserList())) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }
}
