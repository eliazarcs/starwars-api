package eliazarcs.com.starwars.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eliazarcs.com.starwars.api.model.Profile;
import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.service.StarWarsService;

@RestController
public class StarWarsController {
	@Autowired
	private StarWarsService service;
	
	@PostMapping("/users")
    ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User savedUser = service.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
	
	@GetMapping("/users")
	ResponseEntity<List<User>> listUsers() {
		return ResponseEntity.ok(service.findAllUsers()); 
	}
	
	@GetMapping("/profiles")
	ResponseEntity<List<Profile>> listProfiles() {
		return ResponseEntity.ok(service.findProfiles()); 
	}
}
