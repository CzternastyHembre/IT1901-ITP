# The UI module

The UI is made with JavaFX and FXML.

## casinoelements

In this folder the casino chips are made with the use of three different classes. Two enum classes and one main class that uses the enum classes to create the chips.

### CasinoElements

This is a class where some casino elements are made. The UI chips in the Roulette game are all made with this class.

### ChipColor

This is where the enum of the chip color is made, and can be set and get from. This is used in CasinoElements.

### TextValue

This is where the enum of the chip text is made, and can be set and get from. This is used in CasinoElements.

## menuitem

This folder includes all the classes which let the user move between the different parts of the application with the **menuItems** in the UI. The reason for all the different classes extending the MainMenu class is to prevent the same methods being written in all the controllers, instead they can use these classes.

### CasinoMenu

This class extends **LoginMenu** and the menuItem **Lobby** is added so that the user go back to the lobby to either switch game or add more money to their balance.

### LobbyMenu

LobbyMenu extends the **LoginMenu** and allows the other controllers to get and set the current user.

### LoginMenu

This class extends **MainMenu** and the menuItem **Back To Main Menu** added so the user can go back to the main menu to either log in with a different username or create a new one.

### MainMenu

This class allows the user to use the menuItem **exit** to close the application. This is the parent class of all the other classes in this folder.

## AddMoneyController

This controller uses the **AddMoney.fxml**, and the main function of this controller is to add money to the user's balance. It also extends the **CasinoMenu**.

## App

This file launches the application.

## BlackjackController

This is where all the JavaFX logic for the Blackjack game is. It uses the **Blackjack.fxml** as its core and most of the UI is written in code. It also extends the **CasinoMenu** so the user can maneuver between all views in the application.

## CreateUserController

This is where the user creates a new user to play with. This controller uses the **LogIn.fxml** and it extends LoginController. This gives the controller all the same functions as their parent class. By doing this, the CreateUserController does not need a FXML file, but instead uses the same as the LogInController.

## LoginController

This is where the user can log in with an existing username. The controller uses the **LogIn.fxml** and extends the **LoginMenu**.

## RestModel

This is where the logic of how the **create user**, **get user** and **set active user** are connected to the rest API. This means as that in this file, the application communicates with the rest API to either fetch an existing user based on username, create a new user and add it to the server, or set the active user to the one currently using the application.

## RouletteController

This controller contains most of the logic used in the Roulette JavaFX. Only the chips are made by the use of **CasinoElements**. The rest of the JavaFX is in this controller. The controller uses **Roulette.fxml**, which is a pretty simple FXML file. Most of the UI is written in code. This controller also extends **CasinoMenu** so it can use all the menuItems to maneuver between the menus.

## SelectGameController

In this controller the user chooses what game to play, or to add money to their balance. The controller redirects the user to the chosen game or where they can add money. The controller uses the **selectGameView.fxml**. It also extends the **LobbyMenu**.

## SlotsController

SlotsController extends **SlotsDisplay** and therefore can use all the parent's functionality. This controller initializes the UI for the Slots game by using **super** to use some methods in SlotsDisplay.

## SlotsDisplay

This is where all the JavaFX logic for the Slots game is, and it extends **CasinoMenu** so that it can use all the menuItems to redirect the user between the different menus of the game. It uses the **Slots.fxml** as its FXML file.

## StartController

This controller extends the **MainMenu** to use its functionality. This is where the user decides whether to create a new user, or log in with an existing one, and the redirects the user to the chosen view. This controller uses the **Start.fxml**.

## Class diagram:

This class diagram shows how the different classes work togheter.

**Keep in mind** this diagram does **not** show every method in the classes. It only shows the main methods and how the different classes uses each other.

![class diagram](docs/Images/classDiagramUI.png)

## Sequence diagram of the UI

A sequence diagram to show how the application works when creating a new user.

![sequence](docs/Images/sequenceDiagramLast.png)
