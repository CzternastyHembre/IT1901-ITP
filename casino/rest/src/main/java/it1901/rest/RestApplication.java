package it1901.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import savehandler.UserSaveHandler;

@SpringBootApplication
public class RestApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static int portNumber;


    public static void isTest(boolean test){
        if (test){
            portNumber = 8042;
        }
        else{
            portNumber = 8080;
        }
    }

  /**
  * Main class for the springboot webserver.
   *
   * @param args the arguments for the main function.
   */

    public static void main(String[] args) {
        isTest(false);
        new UserSaveHandler().createDirectory();
        SpringApplication.run(RestApplication.class, args);
	}

    public static Object isTestMode(){
        isTest(true);
        return SpringApplication.run(RestApplication.class);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
       factory.setPort(portNumber);
    }
}
