package eliazarcs.com.starwars.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eliazarcs.com.starwars.api.dto.ApiResponse;
import eliazarcs.com.starwars.api.model.Profile;
import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.service.StarWarsService;

@RestController
public class StarWarsController {
	@Autowired
	private StarWarsService service;

	@PostMapping("/users")
	ResponseEntity<ApiResponse<User>> addUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok(new ApiResponse<User>(HttpStatus.OK.value(), service.saveUser(user)));
	}

	@GetMapping("/users")
	ResponseEntity<ApiResponse<List<User>>> listUsers() {
		return ResponseEntity.ok(new ApiResponse<List<User>>(HttpStatus.OK.value(), service.findAllUsers()));
	}

	@GetMapping("/user/{cpf}")
	ResponseEntity<ApiResponse<User>> findUserByCpf(@PathVariable(value="cpf") String cpf) {
		return ResponseEntity.ok(new ApiResponse<User>(HttpStatus.OK.value(), service.findByCpf(cpf)));
	}

	
	@GetMapping("/profiles")
	ResponseEntity<ApiResponse<List<Profile>>> listProfiles() {
		return ResponseEntity.ok(new ApiResponse<List<Profile>>(HttpStatus.OK.value(), service.findProfiles()));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
