package it1901.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import savehandler.UserSaveHandler;
import user.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

import org.testng.junit.*;
import org.junit.jupiter.api.BeforeEach;
import user.User;

import static org.junit.jupiter.api.Assertions.*;

public class RestControllerTest {

    public static final String baseUri = "http://localhost:8080";
    public final Gson gson = new Gson();
    private RestController restController = new RestController();
    private UserSaveHandler userSaveHandler = new UserSaveHandler(true);


    @AfterEach
    public void cleanListAfterTest(){
        userSaveHandler.cleanUserList();
    }

    @Test
    public void canCreateUser() {
        try {
            User testUser = new User("endPointTestUser", 500);
            restController.addUser(testUser);
            assertNotEquals(userSaveHandler.getUserList(), null);
        } catch (IOException e) {
            fail();
        }
    }

    

}
