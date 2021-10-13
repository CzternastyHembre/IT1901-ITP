package saveHandler;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import user.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserSaveHandler {
    public static String SAVE_FILE = "../storage/src/main/resources/users.json";


    public static void cleanUserList() throws IOException {
        List<User> userList = getUserList();
        userList.clear();
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(SAVE_FILE);
        String jsonSaveString = gson.toJson(userList);
        fileWriter.append(jsonSaveString);
        fileWriter.close();
        System.out.println("cleared");
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
        try {
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter(SAVE_FILE);
            String jsonSaveString = gson.toJson(userList);
            fileWriter.append(jsonSaveString);
            fileWriter.close();
            System.out.println("Written!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmpty(){
        File file = new File(SAVE_FILE);
        System.out.println(file.getAbsolutePath());
        return file.length() == 0;
    }

    public static List<User> getUserList() throws IOException {
            if(isEmpty()){
                return new ArrayList<>();
            }
            List<User> userList = new ArrayList<>();
            Scanner sc = new Scanner(new File(SAVE_FILE));
            if (!sc.hasNextLine()){
                return userList;
            }
            String userString = sc.nextLine();
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            userList = gson.fromJson(userString, userListType);
            sc.close();
            return userList;
        }

    public static User getUser(String username) throws IOException {

        for(User user : Objects.requireNonNull(getUserList())){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    // The active user is the first element in UserList
    public static void setActive(User user) throws IOException {
        List<User> userList = getUserList();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(user.getUsername())){
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
            if(userList.get(i).getUsername().equals(user.getUsername())){
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

