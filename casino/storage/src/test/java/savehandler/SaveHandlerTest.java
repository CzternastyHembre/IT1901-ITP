package savehandler;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import user.User;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.ArrayList;



public class SaveHandlerTest {

    @BeforeEach
    public void cleanUserList(){
        UserSaveHandler.cleanUserList();
    }
    //Makes the arrays strings to check if equal, the hash does not allow to be compared.
    @Test
    public void getUsersTest() throws IOException {
        User getUser = new User("bob", 1000);
        User get2User = new User("alice", 1000);
        UserSaveHandler.createUser(getUser);
        UserSaveHandler.createUser(get2User);
        ArrayList<User> myList = new ArrayList<>();
        myList.add(get2User);
        myList.add(getUser);
        assertEquals(myList.toString(), UserSaveHandler.getUserList().toString());
    }

    @Test
    public void resetUserList() throws IOException {
        UserSaveHandler.cleanUserList();
        assertEquals(0,UserSaveHandler.getUserList().size() );
    }

    @Test
    public void getUserTest() throws IOException {
        User user = new User("karen", 500);
        UserSaveHandler.createUser(user);
        assertEquals(user.toString(), UserSaveHandler.getUser("karen").toString());
    }

    @Test
    public void activeUserTest() throws IOException {
        User activeUser = new User("activeUser", 100);
        User nonActiveUser = new User("nonActive", 500);
        UserSaveHandler.createUser(activeUser);
        UserSaveHandler.createUser(nonActiveUser);
        UserSaveHandler.setActive(activeUser);
        assertEquals(activeUser.toString(), UserSaveHandler.getActiveUser().toString());
    }
}
