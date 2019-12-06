package eliazarcs.com.starwars.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import eliazarcs.com.starwars.api.infrastructure.security.JWTAuthenticationService;
import eliazarcs.com.starwars.api.service.StarWarsService;
import eliazarcs.com.starwars.api.util.StarWarsUtil;

@SpringBootApplication
public class Application implements ApplicationRunner  {
	@Autowired
	private StarWarsService service;
	@Autowired
	private ApplicationContext ctx;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		service.saveDefaultProfiles();
		service.saveDefaultUsers();
		StarWarsUtil.appContext = ctx;
	}

}
