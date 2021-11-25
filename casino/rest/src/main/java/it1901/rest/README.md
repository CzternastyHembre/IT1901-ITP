# The Rest module

This module is the Rest API module. It uses the SpringBoot library for setting up a Rest API that handles user saving in the application. 

The module consists of three classes: 

- RestApplication.java
- RestController.java
- UserModelService.java


The RestApplication is the class that starts the server with the main method. The class also has methods for distinguishing between test mode and not. For integration testing the server will run on the port 8042 to seperate testing and non-testing uses. 

The RestController is the controller of the API. Depending on the request from the RestModel class in the UI-module, the corresponding reponse will be returned. The main methods here are creating a user, getting a user, getting a userlist and updating users. 

The UserModelService class is used to do the responses RestController tells it to do. It has the logic of how saving the users and updating them in the API is done. 

Too see documentation for the webservers Http requests and responses, [click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/blob/main/casino/rest/src/main/asciidoc/RestDocumentation.adoc)

## Class diagram: 

![class diagram](docs/Images/classRest.png)
