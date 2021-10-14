# Documentation for release 1

  

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

Including the main features there are three addition features which are designed as menuItems in the menuBar. The features are **Close**(closes the application), **Main Menu**(redirects the user to the main menu) and **Lobby**(redirects the user to the lobby). These features are available depending on where the user is in the application. In the main menu the user can only use the Close function is available, in log-in page, create-user page and lobby page the user can use Close and Main Menu function. Lastly, in roulette and slots the user can use all of these features.

