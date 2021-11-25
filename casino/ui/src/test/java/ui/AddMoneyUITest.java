package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;

import static org.mockito.Mockito.*;

public class AddMoneyUITest extends ApplicationTest {

    private AddMoneyController addMoneyController;
    public final static int START_AMOUNT = 1000;
    private User user = new User("TestUser", START_AMOUNT);

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMoney.fxml"));
        addMoneyController = new AddMoneyController();
        addMoneyController.setUser(user);
        addMoneyController.setRestModel(mock(RestModel.class));
        loader.setController(addMoneyController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    @BeforeEach
    public void beforeEach() {
        user.setBalance(START_AMOUNT);
    }

    @Test
    public void testAddMoney() {

        final Integer amount = 100;
        clickOn("#amountField").write(amount.toString());
        clickOn("#addButton");

        Assertions.assertEquals(user.getBalance(), amount + START_AMOUNT);
    }
}
