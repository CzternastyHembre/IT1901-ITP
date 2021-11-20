
package ui;
import user.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    public static User getUser(String username) throws IOException, InterruptedException {
        String endpoint = baseUri + "/users/" + username;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Content-type", "application/json")
                .uri(URI.create(endpoint))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), User.class);
    }

    public static void updateUser(User user) throws InterruptedException {
        try {
            String endpoint = baseUri + "/users/update";
            String payload = gson.toJson(user);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}