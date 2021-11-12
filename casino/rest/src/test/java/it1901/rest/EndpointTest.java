package it1901.rest;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import savehandler.UserSaveHandler;
import user.User;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RestController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class EndpointTest {

    @AfterEach
    public void cleanUserList(){
        UserSaveHandler.cleanUserList();
    }

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    public void getRequst200ok() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void postRequest() throws Exception {

        User user = new User("test", 500);
        mockMvc.perform(
                        post("/users/add")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void setActiveUserRequest() throws Exception{
        User activeUser = new User("test", 500);

        mockMvc.perform(
                        post("/users/set-active")
                                .content(gson.toJson(activeUser))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
