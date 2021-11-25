# Casino

The Casino application is meant to serve as an entertainment application for users to play different types of casino games.
The users of the application will be able to play blackjack, slots and roulette.
They will also be able to save their profiles with their username and balance, and load the game in later.
There is also an "add money" page, in case the user goes broke. The user can then add more money to their balance and continue playing our games.

The code in the application is divided into three main parts:
core, ui and rest. This helps us easily divide the different parts of the code. The project uses Maven as its building tool.
It also contains JUnit testing for all four modules.

## Techstack

- Java
- Maven
- JavaFX
- SpringBoot
- JUnit

## Running the code:

Start by installing the application:

```powershell
mvn install
```

When the application is installed, you have to set up the API before using the appliaction:

```powershell
cd rest/
mvn spring-boot:run
```

Then run the application by opening another terminal:

```powershell
cd casino/ui/
mvn javafx:run
```

## Convert to executable:

After installing and running the application in the steps above:

```powershell
mvn clean compile javafx:jlink jpackage:jpacakge
```

**You have to be inside the UI module for this to work**

Keep in mind, the webserver has to run for the applcation to work.

## Modules:

The code in the application is divided into three main parts, core, ui and storage to easily divide the different parts of the code. The project uses Maven as its building tool. The project also contains tests for testing the different modules.

### Core

The core module contains the classes and logic that the application uses to
play the different casino games. It is independent of the UI and file-saving.

Since our app is a casino, all the logic of how blackjack, roulette and slots are played,
happens here in the different packages inside the module.
The core module contains classes to handle and represent the logic behind these games.

[Click here](casino/core/src/main/java) to read more about the core module.

### UI

The UI module contains classes which determine how the UI should be manipulated.

The UI seamlessly changes through multiple views such as the "Create User" or "Login" pages,
as well as the different casino game views. Navigation between these pages is simple; a maximum of 3
button clicks to navigate throughout the entire application.

The UI is made with JavaFX and FXML. The FXML files and the controllers of
the FXML files are seperated into different packages.
The controllers are located in the **UI/src/main/java/ui**,
while the FXML files are located in the **main/resources/ui**.

[Click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/RestModel/casino/ui/src/main/java) to read more about the UI module.

### Rest

The Rest module is the module for setting up the REST API.
The rest module uses the framework Springboot to easily set up the API.
The application will save the users both locally and inside the API.
It is a webserver which lets the RestModel class in the UI communicate with the server.
[Click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/RestModel/casino/rest/src/main) to read more about the REST API.

### IntegrationTest

The IntegrationTest module is used to test the application as a whole. It requires all modules to test the different features togheter. This could be creating the user in the UI and making sure the webserver saves the created user. [Click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/integrationTest/src/test/java) to read more about the module.

## Building with Maven

Since this project is a bigger java application, it is useful to use a building tool,
like Maven, to run tests, check the quality of the code, etc.
Our project is configured to use Maven, and therefore has a pom.xml file for the configuration:
The pom.xml file contains different types of information about the project:

- Identification in the form of **groupId, ArtifactId and version-elements**
- Dependencies in the form of **Dependencies-elements**
- Plugins in the form of **Plugin-elements**

Our maven build also includes the following:

- Setup of Java (**Maven-compiler-plugin**)
- Testing (**Maven-surefire-plugin**)
- The running of JavaFx (**javafx-maven-plugin**)
- Checking the quality of the code with spotbugs (**spotbugs-maven-plugin**)
- Test coverage with jacoco (**jacoco-maven-plugin**)
- Code quality with (**checkstyle-maven-plugin**)

## Architecture of the app

A PlantUML illustration showing the module dependencies and their packages.

![appDependencies](docs/Images/archiUML.png)

## Illustrations

Illustrations of the different scenes in the application:

### The opening scene

![start](docs/Images/MainMenu.png)

### Create a user screen

![create_user](docs/Images/CreateUser.png)

### Log in screen

![log_in](docs/Images/LogIn.png)

### The lobby screen

![choose_game](docs/Images/Lobby.png)

### Add money screen

![addMoney](docs/Images/AddMoney.png)

### Blackjack game

![blackjack](docs/Images/Blackjack.png)

### Roulette game

![roulette](docs/Images/Roulette.png)

### Slots game

![slots](docs/Images/Slots.png)
