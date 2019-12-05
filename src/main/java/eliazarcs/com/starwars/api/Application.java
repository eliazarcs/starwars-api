package eliazarcs.com.starwars.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import eliazarcs.com.starwars.api.service.StarWarsService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

public class Application implements ApplicationRunner  {
	@Autowired
	private StarWarsService service;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		service.saveDefaultProfiles();
	}

}
