
package ui;
import com.google.gson.reflect.TypeToken;
import user.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RestModel {
    public static final String baseUri = "http://localhost:8080";
    public static final Gson gson = new Gson();


    public static void createUser(User newUser) throws IOException, InterruptedException {
        String endpoint = baseUri + "/users/add";
        String payload = gson.toJson(newUser);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }


    public static List<User> getUserList() throws IOException, InterruptedException {
        List<User> userList;
        String endpoint = baseUri + "/users";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Content-type", "application/json")
                .uri(URI.create(endpoint))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        userList = gson.fromJson(response.body(), userListType);
        return userList;
    }
    public static User getUser(String username) throws IOException, InterruptedException {
        for (User user : getUserList()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    public static void updateList(List<User> userList) throws IOException, InterruptedException {
        String payload = gson.toJson(userList);
        String endpoint = baseUri + "/users/update";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static void setActive(User user) throws IOException, InterruptedException {
        String payload = gson.toJson(user);
        String endpoint = baseUri + "/users/set-active";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}