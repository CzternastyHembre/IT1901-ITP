# The Rest module

The rest module contains two classes:

- RestApplication.java
- RestController.java
- UserModelService.java

The RestApplication only contains a main method that will start the Springboot server and create a directory in the home directory of the user.

The RestController is the controller of the API. Depending on the request from the RestModel class in the UI-module, the corresponding reponse will be returned.

The UserModelService class is used to do the responses RestController tells it to do. The UserModelService uses saveHandler to write to file. 

Too see documentation for the webservers Http requests and responses, [click here](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2124/gr2124/-/blob/main/casino/rest/src/main/asciidoc/RestDocumentation.adoc)
