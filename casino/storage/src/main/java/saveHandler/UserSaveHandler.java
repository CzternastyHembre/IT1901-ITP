package saveHandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.IOException;
import user.User;


/**
 * Class for saving the users objects
 */
public class UserSaveHandler {
  /**
   * Variable SAVE_FILE is saved as the path to the saving destination
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

  public static boolean isEmpty() {
    File file = new File(SAVE_FILE);
    System.out.println(file.getAbsolutePath());
    return file.length() == 0;
  }

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

  public static User getUser(String username) throws IOException {

    for (User user : Objects.requireNonNull(getUserList())) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  // The active user is the first element in UserList
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

