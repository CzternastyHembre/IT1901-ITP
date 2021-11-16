package it1901.rest;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testng.annotations.AfterMethod;
import savehandler.UserSaveHandler;
import user.User;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RestController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@EnableWebMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class EndpointTest {


    @AfterEach
    public void cleanUserList(){
        UserSaveHandler.cleanUserList();
    }


    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();


    @Test
    public void getRequest200ok() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
                .andDo(document("getRequst200ok"));
    }

    @Test
    public void postRequest() throws Exception {

        User user = new User("addUserTest", 100);
        mockMvc.perform(
                        post("/users/add")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("user",
                        requestFields(
                                fieldWithPath("username")
                                        .description("the username"),
                                fieldWithPath("balance")
                                        .description("the balance"))));
    }

    @Test
    public void setActiveUserRequest() throws Exception{
        User activeUser = new User("setActiveUserTest", 50);

        mockMvc.perform(
                        post("/users/set-active")
                                .content(gson.toJson(activeUser))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("activeUser",
                        requestFields(
                                fieldWithPath("username")
                                        .description("Active user username"),
                                fieldWithPath("balance")
                                        .description("Balance of the active user"))));
    }
}
