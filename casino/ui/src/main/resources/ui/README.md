# The FXML-files

The UI is made with JavaFX and FXML

The FXML-files in this folder contains code of how the different scenes in the application is built. It describes the different components in the scene and where they are placed. 

- The **Start.FXML** file is what you see when you open the application. It contains two buttons, log in and create a user. Depending of what you chose you get sent to a different scene.
- The **Log_in.FXML** file is a simple scene with just a textfield for the user to input their username and a button to approve the username. If the username is an existing username, the user will get to the next scene.
- The **Create_User.FXML** file is similar to the Log_in scene with just a textfield for the user to input their desired username and a button to approve the username. If the username is not already taken, the user will get the username and get transferred to the roulette scene. 
- The **Roulette.FXML** file is the roulette game with many different components. 
