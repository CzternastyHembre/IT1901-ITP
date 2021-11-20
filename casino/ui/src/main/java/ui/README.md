# The UI module

The UI is made with JavaFX and FXML.

## App

This file launches the application.

## Blackjack

This is where all the JavaFX logic for the Blackjack game is. It uses the **Blackjack.fxml** as its core and most of the UI is written as code. It also extends the **CasinoMenu** so the user can maneuver between all the menus in the application.

## Menu

In this folder there are all the controller which can be catogorized as the menu controller. These controller are not related to any of the games, but instead they are a part of the path to get to the games. The AddMoneyController is an exception. This controller can be used to alter the user's balance.

### AddMoneyController

This controller uses the **AddMoney.fxml**, and the main function of this controller is to add money to the user's balance. It also extends the **CasinoMenu**.

### CreateUserController

This is where the user creates a new user to play with. The controller uses the **LogIn.fxml** and it extends LoginController. Which gives the controller all the same functions as their parent class. By doing this, the CreateUserController does not need a FXML file, but instead use the same as the LogInController. It also extends the **LoginMenu**.

### LogInController

This is where the the user can log in with an already existing username. The controller uses the **LogIn.fxml** and extends the **LoginMenu**.

### SelectGameController

In this controller the user chooses what game to play or to add money to their balance. The controller redirects the user to the the chosen game or where they can add money. The controller uses the **selectGameView.fxml**. It also extends the **LobbyMenu**.

### StartController

This controller extends the **MainMenu** to use its functionality. This is where the user decides whether or not to create a new user or log in with an existing one, and the redirects the user to the chosen one. This controller uses the **Start.fxml**.

## MenuItem

In this folder are all the classes which let the user move between the different parts of the application with the **menuItems** in the UI. The reason for all the different classes extending the MainMenu class is to prevent the same methods being written in all the controllers, instead they can use these classes.

### CasinoMenu

This class extends **LoginMenu** and in this class the menuItem **Lobby** is added so that the user go back to the lobby to either switch game or add more money to their balance.

### LobbyMenu

It extends the **LoginMenu** and allows the the other controllers to get the current user and also set the user.

### LoginMenu

This class extends **MainMenu** and in this class the menuItem **Menu Menu** added so the user can go back to the main menu to either log in with a different username or to create a new one.

### MainMenu

This class allows the user to use the menuItem **exit** to close the application. This is the parent class of all the other classes in this folder.

## RestModel

This is were the logic of how the create user, get the user and set active user connected to the rest API. This means as that in this file the application communicates with the rest API to either fetch an existing user based on username, creates a new user and adds it to the server or sets the active user to the one currently using the application.

## Roulette

In this folder there are the controllers and classes related to the Roulette game.

### CasinoElements

This is a class where some of the casino elements are made. The UI chips in the Roulette game are are all made with this class.

### RouletteController

In this controller there is most of the logic used in the Roulette JavaFX, only the chips are made by the use of **CasinoElements**. The rest of the JavaFX is in this controller. The controller uses **Roulette.fxml**, which is a pretty simple FXML file. Most of the UI is written in code. This controller also extends **CasinoMenu** so it can use all of the menuItems to maneuver between the menus.

## Slots

In this folder are all the controllers and classes related to the Slots game.

### SlotsController

SlotsController extends **SlotsDisplay** and therefore can use all the parents functionality. This controller initializes the UI for the Slots game by using **super** to use some of the methods in SlotsDisplay.

### SlotsDisplay

This is where all the JavaFX logic for the Slots game is and it extends CasinoMenu so that it can use all of the menuItems to redirect the user between the different menus of the game. It uses the **Slots.fxml** as its FXML file. It also extends the **CasinoMenu**.

## Sequence diagram of the UI

This is a sequence diagram of how the UI path from launch of application to playing Roulette.

![sequence](docs/Images/rouletteDiagram.png)
