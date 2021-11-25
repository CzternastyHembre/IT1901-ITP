package api.rest;


import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
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
import user.User;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RestController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "src/main/asciidoc")
@EnableWebMvc
@ContextConfiguration(classes = { RestController.class, UserModelService.class, RestApplication.class })
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})

public class
EndpointTest {
    private final UserModelService userModelService = new UserModelService();

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();


    @BeforeEach
    public void cleanUserList() throws Exception {
        mockMvc.perform(
                delete("/delete/testUser")
        );
        userModelService.deleteUser("testUser");
    }


    @Test
    public void getRequest200ok() throws Exception {

        User testUser = new User("testUser", 500);

        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(testUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
                .andDo(document("getRequest200ok"));
    }

    @Test
    public void postRequest() throws Exception {

        User user = new User("testUser", 100);
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
        User getUser = new User("testUser", 100);
        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(getUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(get("/users/testUser")).andDo(print()).andExpect(status().isOk())
                .andDo(document("getUser"));
    }

    @Test
    public void updateUserTest() throws Exception {
        User originalUser = new User("testUser", 100);
        mockMvc.perform(
                post("/users/add")
                        .content(gson.toJson(originalUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        User updatedUser = new User("testUser", 500);
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

    @Test
    void deleteUserTest() throws Exception {
        User user = new User("testUser", 100);
        mockMvc.perform(
                        post("/users/add")
                                .content(gson.toJson(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(document("updatedUser",
                        requestFields(
                                fieldWithPath("username")
                                        .description("the username"),
                                fieldWithPath("balance")
                                        .description("the balance"))));

        this.mockMvc.perform(delete("/delete/testUser")).andDo(print()).andExpect(status().isOk())
                .andDo(document("deleteUser"));
    }
}

