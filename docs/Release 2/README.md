# Documentation for release 2

  

## Maven build

The casino application uses the building tool Maven to build and run the application. This is done to automate the process of downloading and managing the dependencies in the project. 

## GitPod

  
The application can be executed and run with GitPod.  Launch this by pressing the GitPod badge in the root [README.md](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/blob/main/README.md) 

## Running the code:

Start by routing into the casino directory. 

```powershell
cd casino
```

When inside the casino directory, the next step is to install the project:
```powershell
mvn install
cd ui/
mvn javafx:run
````

## Status of the project
In this release there are main four features. Logging in with an existing user, creating a user, playing roulette and playing slots. After logging in or creating a user the user is redirected to a lobby menu where the user can choose between playing roulette or slots. Then the user is redirected to the chosen game.

Including the main features there are three addition features which are designed as menuItems in the menuBar. The features are **Close**(closes the application), **Main Menu**(redirects the user to the main menu) and **Lobby**(redirects the user to the lobby). These features are available depending on where the user is in the application. In the main menu the user can only the Close feature is available, in log-in page, create-user page and lobby page the user can use the Close and Main Menu features. Lastly, in roulette and slots the user can use all of these features.

## Code architecture

The project is a multi module project to easily divide the different parts of the code. The project is divided in three main modules: Core, UI and Storage. 

### Core
The [core module](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/core) contains all the classes and logic that the application uses to play the different casino games. It is independent of all the UI and filesaving.

Since our app is a casino, all the logic involving the roulette, slots and user happens here in the different packages inside the module. The core module contains classes to handle and represent the logic behind these games.

### UI
All the classes and logic of how the UI and buttons work happens in the [UI module](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui). The UI of our project is to show a start-page where users could either log in or create a new user. Then the user will be routed to the lobby page where the user can choose between roulette and slot. The user used in the log in or the newly created user is the active user. 

The UI is made with JavaFX, FXML, css and images. Those are divided into different folders. The FXML controllers are in **[UI/src/main/java/ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/java/ui)**. FXML, css and images are in **[UI/src/main/java/resources](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources)** where they are further divided into new folders. The FXML files are in **[UI/src/main/java/resources/ui](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources/ui)**, the css is in **[UI/src/main/java/resources/css](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources/css)** and the images are in **[UI/src/main/java/resources/images](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/tree/main/casino/ui/src/main/resources/images)**.

### Storage 





# Workflow

For ensuring a steady and good workflow the group of developers have used the framework scrum with milestones and issues.


## Milestones

The group uses milestones for monitoring the sprints throughout the project. The milestone for the second release is called **Group Assignment 2**. 
The sprints will have the duration 1-2 weeks, this depends on the size of the milestone. 


## Issues

Throughout the project the group uses issues to easily delegate tasks to other developers working on the project. The issues was made with labels, a due date and an assignee. 
The issues would also have a description that would explain what needs to be done in the issue. For bigger issues there would also be checkboxes for the developers to check if a part of the issue was finished. 

When commiting a new feature, in the commit message the developers would use the "#" to link to the issue they have been working on or finished. This ensures that the when a commit happens the other developers could see what issue it belongs to. 

### Labels
All of the issues that was created in the first release has a label to mark what kind of issue or task this is. The main labels that was used was: 
- Documentation
- Incident
- us-2
- Bug

### Boards
The board functionality was used to place the issues in three different areas:
- To-do 
- Doing
- Done

This was used so that the developers could easily get an understanding of what is going on in the project. 

## Branch
  
When working on new features in the application it was very important that the developers would make new branches for that specific feature. The branch should have an appropriate and informative name to let the other developers know what the intention of the branch is.  This is done to ensure that the **main** branch would always work. We also push the branches to remote so others can test the new functionalities before we merge the new them into **main**.

### Merge-request

Before merge-requesting the developer of the branch had to inform the other developers in the group that he would be performing a merge-request. The requirement for performing the merge-request would be that the application in the branch with the new feature is running as intended in the issue. As well as the merge-request must be approved and merged in by another developer then themself. As mentioned in **Branch**, since the branches are pushed to remote, we can test the new functionalities and bulletproof that the new changes are functional and coded well. 


## Code quality

For ensuring good code quality throughout the project, the following plugins was added to help:

-  Test coverage with Jacoco (**jacoco-maven-plugin**)
Jacoco is a maven-plugin that collects and presents the information of the codecoverage in the code. 

- Checking the quality of the code with spotbugs (**spotbugs-maven-plugin**)
Spotbugs is another maven-plugin that analyzes the code for regular bugs.

Both of these plugins run when using the **maven-verify lifecycle.** 

### Tests


## Illustrations
Illustrations of what the end product is going to look like:
