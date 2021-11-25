package ui.menuitem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * This class allows the user to use the menuItem exit to close the application.
 * This is the parent class of all the other classes in this folder.
 */
public abstract class MainMenu implements Initializable {

  @FXML
  protected AnchorPane anchorPane;
  @FXML
  protected FXMLLoader loader = new FXMLLoader();

  protected Menu menu = new Menu("Menu");

  /**
   * Method that creates the menubar in the AnchorPane.
   */

  private void createMenuBar() {
    MenuBar menuBar = new MenuBar();
    menuBar.getMenus().add(menu);

    menu.setId("menuButton");

    anchorPane.getChildren().add(menuBar);
    AnchorPane.setLeftAnchor(menuBar, 0.0);
    AnchorPane.setRightAnchor(menuBar, 0.0);
  }

  /**
   * Method that creates the menuItem and adds them, then calls the method createMenuBare.
   */

  protected void createMenu() {
    MenuItem menuItem = new MenuItem("Close");
    menuItem.setId("exit");
    menuItem.setOnAction(event -> System.exit(0));
    menu.getItems().add(menuItem);
    createMenuBar();
  }
}
