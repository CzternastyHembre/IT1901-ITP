# Casino

The Casino application is meant to serve as an entertainment application for users to play different types of casino games. The users of the application will be able to play blackjack, slots and roulette. The users will also be able to save their profiles with username and balance and load the game in later.

The code in the application is divided into three main parts, core, ui and storage to easily divide the different parts of the code. The project uses Maven as its building tool. The project also contains tests for testing the different modules.

## Core

The core module contains all the classes and logic that the application uses to play the different casino games. It is independent of all the UI and filesaving.

Since our app is a casino, all the logic of how blackjack, roulette and slots are played, happens here in the different packages inside the module. The core module contains classes to handle and represent the logic behind these games.

[Click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/RestModel/casino/core/src/main/java) to read more about the core module.

## UI

All the classes and logic of how the UI and buttons work happens in the UI module. The UI of our project is to show a start-page where users could either log in or create a new user. Further the user should be able to play different types of casino games like roulette, blackjack and slots.

The UI is made with JavaFX and FXML, where the FXML files and the controllers of the FXML files are divided in different packages, where the controllers are in the **UI/src/main/java/ui** and the FXML files are in the **main/resources/ui**.

[Click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/RestModel/casino/ui/src/main/java) to read more about the UI module.

## Rest

The Rest module is the module for setting up the REST API. The rest module uses the framwork Springboot to easily set up the API. The application will save the users both locally and inside the API. It is a webserver such that the RestModel class in the UI can communicate with the server. [Click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/RestModel/casino/rest/src/main) to read more about the REST API.

## Building with Maven

Since this project is a bigger java application, it is usefull to use a building tool, like Maven, to run the tests, check the quality of the code, etc. Our project is configured to use Maven and therefore has a pom.xml file for the configuration:
The pom.xml file contains different types of information about the project:

- Identefication in the form of **groupId, ArtifactId and version-elements**
- Dependecncies in the form of **Dependecncies-elements**
- Plugins in the form of **Plugin-elements**

Our maven build also has the follwing:

- Setup of Java (**Maven-compiler-plugin**)
- Testing (**Maven-surefire-plugin**)
- The running of JavaFx (**javafx-maven-plugin**)
- Checking the quality of the code with spotbugs (**spotbugs-maven-plugin**)
- Test coverage with jacoco (**jacoco-maven-plugin**)
- Code quality with (**checkstyle-maven-plugin**)

## Architecture of the app

A PlantUML illustration showing the module dependencies and their packages.

![appDependencies](docs/Images/updatedModuleDiagram.png.png)

## Illstrations

Illustrations of what the end product is going to look like:

### The opening scene

![start](docs/Images/MainMenu.png)

### Create a user screen

![create_user](docs/Imagess/CreateUser.png)

### Log in screen

![log_in](docs/Images/LogIn.png)

### The lobby

![choose_game](docs/Images/Lobby.png)

### Blackjack game

![blackjack](docs/Images/BlackJack.png)

### Roulette game

![roulette](docs/Images/Roulette.png)
