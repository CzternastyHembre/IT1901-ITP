# Casino
The code in the application is divided into three main parts, core, ui and storage to easily divide the different parts of the code.

## Core
The core module contains all the classes and logic that the application uses to play the different casino games. It is independent of all the UI and filesaving. 

Since our app is a casino, all the logic of how blackjack, roulette and slots are played, happens here in the different packages inside the module. The core module contains classes to handle and represent the logic behind these games.

## UI
All the classes and logic of how the UI and buttons work happens in the UI module. The UI module is divided by controllers and FXML files in different packages. The UI is created by JavaFX and FXML files inside the Resources package in the module.
The controllers are saved in main/java/ui package. 

## Storage
The storage module contains all the classes and files for saving (reading and writing to files). Our storage module uses JSON-files for filesaving.

## Building with Maven
Since this project is a bigger java application, it is usefull to use a building tool, like Maven or Gradle, to run the tests, check the quality of the code, etc. Our project is configured to use Maven and therefore has a pom.xml file for the configuration: 
The pom.xml file contains different types of information about the project:
- Identefication in the form of **groupId, ArtifactId and version-elements**
