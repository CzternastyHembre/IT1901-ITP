package savehandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import user.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SaveHandlerTest {

    private final UserSaveHandler userSaveHandler = new UserSaveHandler(true);


    @BeforeEach
    public void clearUserList(){
        userSaveHandler.updateFile(new ArrayList<>());
    }


    //Makes the arrays strings to check if equal, the hash does not allow to be compared.
    @Test
    public void getUsersTest() throws IOException {
        userSaveHandler.updateFile(new ArrayList<>());
        User getUser = new User("bob", 1000);
        User get2User = new User("alice", 1000);
        userSaveHandler.createUser(getUser);
        userSaveHandler.createUser(get2User);
        ArrayList<User> myList = new ArrayList<>();
        myList.add(get2User);
        myList.add(getUser);
        assertEquals(myList.toString(), userSaveHandler.getUserList().toString());
    }

    @Test
    public void resetUserList() throws IOException {
        userSaveHandler.updateFile(new ArrayList<>());
        assertEquals(0, userSaveHandler.getUserList().size());
    }

    @Test
    public void getUserTest() throws IOException {
        User user = new User("karen", 500);
        userSaveHandler.createUser(user);
        assertEquals(user.toString(), Objects.requireNonNull(userSaveHandler.getUser("karen")).toString());
    }
}
