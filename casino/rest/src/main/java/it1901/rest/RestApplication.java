package it1901.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import savehandler.UserSaveHandler;

import java.io.IOException;

@SpringBootApplication
public class RestApplication {



	public static void main(String[] args) {
		new UserSaveHandler();
		SpringApplication.run(RestApplication.class, args);
			}
	}
