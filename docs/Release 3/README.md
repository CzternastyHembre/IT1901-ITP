# Documentation for release 3

## Maven build

The casino application uses the building tool Maven to build and run the application. This is done to automate the process of downloading and managing the dependencies in the project.

## GitPod

The application can be executed and run with GitPod. Launch this by pressing the GitPod badge in the root [README.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/blob/main/README.md)

It is recommended to clone the project and run the application locally. The tests for the UI can be unreliable and give failures. The animations are also poorly visualized in GitPod. 

## Running the code:


Start by installing the appication:

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

## Changes from previous release

- Moved **saveHandler** from storage into a own folder in core and deleted storage.
- Created **Blackjack** so the user has another game to choose between.
- Created a **rest module** and a **rest API**.
- Created **menuItems UIs** so that the same code in every UI would not be written multiple times. Instead they extends those menuItems.
- Deleted the **CreateUser.fxml** and instead added functionality so that only one FXML is needed for both **LoginController** and **CreateUserController**. **CreateUserController** also extends **LoginController** to get rid of duplicate code.
- Added **README** files in core, rest and ui to briefly explain their functionality.
- Added **Jpackage** and **Jlink** such that users can convert the application to a executable desktop application.
- Added **SpringBoot** framework for the rest module. Users will now be saved on a webserver.
- Added new module **integrationTest** for integration testing of the application.

## Status of the project

In this release there are six main features. Logging in with an existing user, creating a user, playing roulette, playing slots and playing blackjack. After logging in or creating a user the user is redirected to a lobby menu where the user can choose between playing roulette, slots or blackjack. Then the user is redirected to the chosen game. The last feature allows the user to add more money to their balance. This is available from the lobby menu.

Including the main features there are three additional features which are designed as menuItems in the menuBar. The features are **Close**(closes the application), **Main Menu**(redirects the user to the main menu) and **Lobby**(redirects the user to the lobby). These features are available depending on where the user is in the application. In the main menu only the Close feature is available, in log-in page, create-user page and lobby page the Close and Main Menu features are available. Lastly, in addMoney, roulette, slots and blackjack all of the features are available.

## Code architecture

The project is a multi module project to easily divide the different parts of the code. The project is divided in four main modules: Core, UI, Rest and integrationTest.

### Core

The [core module](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/core/src/main/java) contains all the classes and logic that the application uses to play the different casino games. It is independent of all the other modules.

Since our app is a casino, all the logic involving the roulette, slots, blackjack, user and writing and reading from saved data happens here in the different packages inside the module. The core module contains classes to handle and represent the logic behind these games.

### UI

All the classes and logic of how the UI and buttons work happens in the [UI module](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/java/ui). The UI of our project is to show a start-page where users could either log in or create a new user. Then the user will be routed to the lobby page where the user can choose between roulette, slot, blackjack and adding money. The user used in the log in or the newly created user is passed throughout the controllers inside of the login page.

The UI is made with JavaFX, FXML, CSS and images. Those are divided into different folders. The FXML controllers are in **[UI/src/main/java/ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/java/ui)**. FXML, css and images are in **[UI/src/main/java/resources](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources)** where they are further divided into new folders. The FXML files are in **[UI/src/main/java/resources/ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources/ui)**, the css is in **[UI/src/main/java/resources/css](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources/css)** and the images are in **[UI/src/main/java/resources/images](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources/images)**.

### Rest

The rest module uses the Springboot framework for setting up an API. The classes in this module are used to communicate between the webserver and the application. By setting up an API, the users are being saved on a webserver and also locally. The UI module will send Http requests to the API while the RestController will decide what happens with these requests.

To see how these request and reponses from the webserver look like, [click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/blob/47-create-documentations-for-release-3/casino/rest/src/main/asciidoc/RestDocumentation.adoc).

### IntegrationTest

The integrationTest module is module for testing the application as a whole. Testing within the other modules will not involve each other and only test their own functionality. During the integration testing the application will test updating the user, creating new users and logging in with existing users. This will involve both the rest module and the UI module.

# Workflow

For ensuring a steady and good workflow the group of developers have used the framework scrum with milestones and issues.
The group uses milestones for monitoring the sprints throughout the project. The milestone for the third release is called **Third Group Assignment**. The sprints will have the duration 1-2 weeks, this depends on the size of the milestone.

## Issues

Throughout the project the group uses issues to easily delegate tasks to other developers working on the project. The issues was made with labels, a due date and an assignee.
The issues would also have a description that would explain what needs to be done in the issue. For bigger issues there would also be checkboxes for the developers to check if a part of the issue was finished.

When commiting a new feature, the developers will have a header with the type of the commit(example: **docs**), a description and a footer with the issue(**Issue: #X**). This ensures that the when a commit happens the other developers could see what issue it belongs to.

### Labels

All of the issues that was created in the first release has a label to mark what kind of issue or task this is. The main labels that was used was:

- Documentation
- Incident
- Infrastructure
- Feature
- Saving
- API
- Test
- Bug
- Cleaning

### Boards

The board functionality was used to place the issues in three different areas:

- To-do
- Doing
- Done

This was used so that the developers could easily get an understanding of what is going on in the project.

## Branch

When working on new features in the application it was very important that the developers would make new branches for that specific feature. The branch should have an appropriate and informative name to let the other developers know what the intention of the branch is. To ensure that the branches had proper names we used the function in gitlab issues to branch out from the issue created. This made the branch name have the same name as the issue it regards and the issue number.

The reason for this is to ensure that the **main** branch would always work. We also push the branches to remote so others can test the new functionalities before we merge them into **main**.

### Merge-request

Before merge-requesting the developer of the branch had to inform the other developers in the group that he would be performing a merge-request. The requirement for performing the merge-request would be that the application in the branch with the new feature is running as intended in the issue. As well as the merge-request must be approved and merged in by another developer then themself. As mentioned in **Branch** we push the branches to remote, so we can test the new functionalities and bulletproof that the new changes are functional and coded well. The merge requst will also have header, description and a footer to add more information about why the merge request was being done and potentially what issue it will be closing.

## Code quality

For ensuring good code quality throughout the project, the following plugins was added to help:

- Test coverage with Jacoco (**jacoco-maven-plugin**)
  Jacoco is a maven-plugin that collects and presents the information of the codecoverage in the code.

- Checking the quality of the code with spotbugs (**spotbugs-maven-plugin**)
  Spotbugs is another maven-plugin that analyzes the code for regular bugs.

- Enuring writing Java code standard with checkstyle (**checkstyle-maven-plugin**)
  Checkstyle is a maven-plugin that makes sure the developers are writing clean and readable code.

All of the plugins are run using the **verify** lifecycle.

### Tests

We have created tests to check if the method, logic and features in the different classes function as they are intended to.

The testing was done individually for the different modules. During the UI tests, the developers mocked an API responding to the UI sending Http requests. This was done to focus on just testing the UI and seperate as much as possible. While this resulted that the **RestModel** class in UI that sends Http request gets a low JaCoCo coverage. This is being indirectly tested during the integration testing with the API and the UI working together.

For this release our goal was that the Jacoco test coverage would have a minimum percentage of **80%** in each module to ensure good code quality.

## Illustrations

Illustrations of the end product:

![start](docs/Images/MainMenu.png)
![create_user](docs/Images/CreateUser.png)
![log_in](docs/Images/LogIn.png)
![choose_game](docs/Images/Lobby.png)
![add_money](docs/Images/AddMoney.png)
![blackjack](docs/Images/Blackjack.png)
![roulette](docs/Images/Roulette.png)
![slots](docs/Images/Slots.png)
