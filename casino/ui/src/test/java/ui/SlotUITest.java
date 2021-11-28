package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import user.User;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class SlotUITest extends ApplicationTest{
    private SlotsController slotsController;
    private User user = new User("slotsTest", 100);

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Slots.fxml"));
        slotsController = new SlotsController();
        slotsController.setUser(user);
        slotsController.setRestModel(mock(RestModel.class));
        loader.setController(slotsController);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }



    @Test
    void betAndSpin() {
        var prevAvgPayout = slotsController.getAvgPayout().getText();
        var prevBalanceNum = slotsController.getBalanceNum().getText();
        var prevNetGainNum = slotsController.getNetGainNum().getText();
        var prevCurrentBetNum = slotsController.getCurrentBetNum().getText();
        var prevPayoutNum = slotsController.getPayoutNum().getText();
        var prevComboSlot = slotsController.getComboSlot().getText();
        var prevSpinsCounter = slotsController.getSpinsCounter().getText();

        clickOn("#betField").write("10");
        clickOn("#betButton");
        for (HBox box : slotsController.getHboxesList()){
            assertTrue(box.getChildren().size()!=0);
        }
        sleep(4000);
        assertEquals("", slotsController.getBetField().getText());
        assertNotEquals(prevAvgPayout, slotsController.getAvgPayout().getText());
        assertNotEquals(prevBalanceNum, slotsController.getBalanceNum().getText());
        assertNotEquals(prevComboSlot, slotsController.getComboSlot().getText());
        assertNotEquals(prevCurrentBetNum, slotsController.getCurrentBetNum().getText());
        assertNotEquals(prevNetGainNum, slotsController.getNetGainNum().getText());
        assertNotEquals(prevSpinsCounter, slotsController.getSpinsCounter().getText());
        if (Double.parseDouble(slotsController.getPayoutNum().getText()) !=0.0 ){
            assertNotEquals(prevPayoutNum, slotsController.getPayoutNum().getText());
        }
    }

    @Test
    void testKeepButton(){
        clickOn("#betField").write("10");
        clickOn("#keepBetButton");
        clickOn("#betButton");
        sleep(4000);
        assertEquals("10", slotsController.getBetField().getText());
    }

}
