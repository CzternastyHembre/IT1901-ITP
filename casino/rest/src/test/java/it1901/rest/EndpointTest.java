package it1901.rest;


import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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
@AutoConfigureRestDocs(outputDir = "src/main/asciidoc")
@EnableWebMvc
@ContextConfiguration(classes = { RestController.class, UserModelService.class, RestApplication.class })
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})

public class EndpointTest {
    private UserSaveHandler userSaveHandler = new UserSaveHandler(true);
    private UserModelService userModelService = new UserModelService();

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @AfterEach
    public void cleanUserList(){
        userModelService.cleanUserList();
    }


    @Test
    public void getRequest200ok() throws Exception {

        User testUser = new User("testUser", 500);
        User mappingUser = new User("mappingUser", 100);

        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(testUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(mappingUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
                .andDo(document("getRequest200ok"));
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
    public void getUserTest() throws Exception {
        User getUser = new User("gettingUser", 100);
        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(getUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(get("/users/gettingUser")).andDo(print()).andExpect(status().isOk())
                .andDo(document("getUser"));
    }

    @Test
    public void updateUserTest() throws Exception {
        User originalUser = new User("updatedUser", 100);
        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(originalUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("updatedUser",
                        requestFields(
                                fieldWithPath("username")
                                        .description("the username"),
                                fieldWithPath("balance")
                                        .description("the balance"))));

        User updatedUser = new User("updatedUser", 500);
        mockMvc.perform(
                post("/users/update")
                        .content(gson.toJson(updatedUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("updatedUser",
                        requestFields(
                                fieldWithPath("username")
                                        .description("the username"),
                                fieldWithPath("balance")
                                        .description("the balance"))));
    }
}

