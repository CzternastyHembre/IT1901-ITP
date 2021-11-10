package savehandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


/**
 * Class for saving the users objects.
 */
public class UserSaveHandler {
  /**
   * Variable SAVE_FILE is saved as the path to the saving destination.
   */
  private static Path SAVE_FILE = Paths.get(System.getProperty("user.home"), "CasinoData", "users.json");

  public UserSaveHandler(){

  }

  public static void createDirectory() throws IOException {
    String path = String.valueOf(SAVE_FILE);
    System.out.println(path.replace("/users.json", ""));
    path = path.replaceAll("/users.json", "");
    Files.createDirectory(Path.of(path));
  }




  // When running with "javafx:run", the working directory will be "ui".
  // This method removes the path into "ui", so the path finds the file in "data"
//  private static void adaptFilePath() {
//    String s = SAVE_FILE.replace("/ui", "");
//    s = s.replace("/casino/core/core", "/casino/core");
//    s = s.replace("/rest", "");
//    SAVE_FILE = s;
//  }

  /**
   * Clears the user arrayList and overwrites the file with the new empty arraylist.
   */

  public static void cleanUserList() {

    try (FileWriter fileWriter = new FileWriter(String.valueOf(SAVE_FILE), StandardCharsets.UTF_8)) {
      fileWriter.write("");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * Gets the existing userList and add a user to this. Updates the file with the new user.
   *
   * @param user the user that is being created.
   */

  public static void createUser(User user) throws IOException {
    List<User> userList = getUserList();
    if (userList.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
      throw new IllegalArgumentException("Username is taken");
    }
    userList.add(0, user);
    updateFile(userList);
  }


  private static void updateFile(List<User> userList) {
    try (FileWriter fileWriter = new FileWriter(String.valueOf(SAVE_FILE), StandardCharsets.UTF_8)) {
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

  public static boolean isEmpty() {
    File file = new File(String.valueOf(SAVE_FILE));
    return file.length() == 0;
  }

  /**
  * Reads the file SAVE_FILE and return an arrayList of objects user.
  *
  * @return userList is the arrayList of the object user.
   */

  public static List<User> getUserList() throws IOException {
    if(isEmpty()) {
      FileWriter fileWriter = new FileWriter(String.valueOf(SAVE_FILE), StandardCharsets.UTF_8);
      fileWriter.write("");
      return new ArrayList<>();
      }
      Scanner sc = new Scanner(new File(String.valueOf(SAVE_FILE)), StandardCharsets.UTF_8);
      String userString = sc.nextLine();
      Gson gson = new Gson();
      ArrayList<User> userList;
      Type userListType = new TypeToken<ArrayList<User>>() {
      }.getType();
      userList = gson.fromJson(userString, userListType);
      sc.close();
      return userList;
    }

  /**
   * Checks if there exist a user with the given username.
   *
   * @param username the username that is being checked.
   * @return null or the object user with the username.
   */

  public static User getUser(String username) throws IOException {

    for (User user : Objects.requireNonNull(getUserList())) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Makes the user given the active user, used to log in.
   *
   * @param user is the user that is being logged in and set to active.
   */

  public static void setActive(User user) throws IOException {
    List<User> userList = getUserList();
    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getUsername().equals(user.getUsername())) {
        userList.remove(i);
        break;
      }
    }
    userList.add(0, user);
    updateFile(userList);
  }
  /**
   * Updates a users balance, used if users wins or loses money.
   *
   * @param user is the user that is being updated.
   */

  public static void updateUser(User user) throws IOException {
    List<User> userList = getUserList();
    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getUsername().equals(user.getUsername())) {
        userList.remove(userList.get(i));
        userList.add(0, user);
      }
    }
    updateFile(userList);
  }


  public static User getActiveUser() throws IOException {
    return getUserList().get(0);
  }

  public static void main(String[] args) throws IOException {
    UserSaveHandler.cleanUserList();
  }
}

