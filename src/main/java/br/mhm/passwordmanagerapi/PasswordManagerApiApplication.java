package br.mhm.passwordmanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PasswordManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordManagerApiApplication.class, args);
	}

}
