package it1901.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import savehandler.UserSaveHandler;

@SpringBootApplication
public class RestApplication {

  /**
  * Main class for the springboot webserver.
   *
  * @param args the arguments for the main function.
  */

    public static void main(String[] args) {
    new UserSaveHandler().createDirectory();
    SpringApplication.run(RestApplication.class, args);
	}
}
