package ui;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import user.User;


/**
 * A class for sending HTTP request to API from UI.
 *
 */

public class RestModel {
  public String baseUri;
  public static final Gson gson = new Gson();

  /**
   * Constructor for checking if a test or not.
   *
   * @param test is a boolean. Will have different baseURI.
   *             Is being used in integration test for testing on different port.
   */

  public RestModel(boolean test) {
    if (test) {
      baseUri = "http://localhost:8042";
    } else {
      baseUri = "http://localhost:8080";
    }
  }

  public RestModel() {
    this(false);
  }

  /**
   * Method for creating a user. Send Http request to /users/add.
   * Converts the user to json using Gson.
   *
   * @param newUser the user that is being created.
   * @throws InterruptedException for sending Http request.
   */

  public void createUser(User newUser) throws InterruptedException {
    try {
      String endpoint = baseUri + "/users/add";
      String payload = gson.toJson(newUser);
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

  /**
   * Methods for getting a user based on the username.
   * Send a get request to /users/username to get the user.
   *
   * @param username that is being requested by the UI.
   * @return returns the user as an object using gson to convert.
   * @throws InterruptedException for sending http request.
   * @throws IOException for using the client.send.
   */

  public User getUser(String username) throws InterruptedException, IOException {
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

  /**
   * Method for updating a user, used for updating balance.
   * Sends a POST request with the user being updated.
   *
   * @param user the user that is being updated.
   * @throws InterruptedException for sending Http request.
   */

  public void updateUser(User user) throws InterruptedException {
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

  /**
   * Deletes a user from the webserver.
   * Send a DELETE request to /delete/username
   *
   * @param username The username that is being deleted.
   * @throws InterruptedException for sending Http request.
   */

  public void deleteUser(String username) throws InterruptedException {
    try {
      String endpoint = baseUri + "/delete/" + username;
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .header("Content-type", "application/json")
                .uri(URI.create(endpoint))
                .build();
      client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
