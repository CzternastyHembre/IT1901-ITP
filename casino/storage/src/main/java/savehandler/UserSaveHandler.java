package savehandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
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

  public static final String SAVE_FILE = "../storage/src/main/resources/users.json";

  /**
   * Clears the user arrayList and overwrites the file with the new empty arraylist.
   */

  public static void cleanUserList() {
    try (FileWriter fileWriter = new FileWriter(SAVE_FILE, StandardCharsets.UTF_8)) {
      List<User> userList = getUserList();
      userList.clear();
      Gson gson = new Gson();
      String jsonSaveString = gson.toJson(userList);
      fileWriter.append(jsonSaveString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
    /**
     * Gets the exisiting userlist and add a user to this. Updates the file with the new user.
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
    try (FileWriter fileWriter = new FileWriter(SAVE_FILE, StandardCharsets.UTF_8)) {
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
    File file = new File(SAVE_FILE);
    System.out.println(file.getAbsolutePath());
    return file.length() == 0;
  }

    /**
     * Reads the file SAVE_FILE and return an arrayList of objects user.
     *
     * @return userList is the arrayList of the object user.
     */
  public static List<User> getUserList() throws IOException {
    if (isEmpty()) {
      return new ArrayList<>();
    }
    List<User> userList = new ArrayList<>();
    Scanner sc = new Scanner(new File(SAVE_FILE), StandardCharsets.UTF_8);
    if (!sc.hasNextLine()) {
      return userList;
    }
    String userString = sc.nextLine();
    Gson gson = new Gson();
    Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
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
}

