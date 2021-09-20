# Casino
The code in the application is divided into three main parts, core, ui and storage to easily divide the different parts of the code.

## Core
The core module contains all the classes and logic that the application uses to play the different casino games. It is independent of all the UI and filesaving. 

Since our app is a casino, all the logic of how blackjack, roulette and slots are played happens here in the different packages inside the module

## UI
All the classes and logic of how the UI and buttons work happens in the UI module. The UI module is divided by controllers and FXML files in different packages. The UI is created by JavaFX and FXML files inside the Resources package in the module.
The controllers are saved in main/java/ui package. 

## Storage
The storage module contains all the classes and files for saving (reading and writing to files). Our storage module uses JSON-files for filesaving.
