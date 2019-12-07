package eliazarcs.com.starwars.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

import java.util.Collections;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eliazarcs.com.starwars.api.Application;
import eliazarcs.com.starwars.api.dto.MovieCharacter;
import eliazarcs.com.starwars.api.dto.Specie;
import eliazarcs.com.starwars.api.infrastructure.security.StarWarsUserDetail;
import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.repository.ProfileRepository;
import eliazarcs.com.starwars.api.repository.UserRepository;

@SpringBootTest(classes = Application.class)
@TestExecutionListeners(listeners = MockitoTestExecutionListener.class)
public class StarWarServiceTests extends AbstractTestNGSpringContextTests {
	@Autowired
	private StarWarsService service;

	@MockBean
	UserRepository userRepo;

	@MockBean
	private ProfileRepository profileRepo;

	@Test(priority = 1)
	public void findByCpf() {
		Mockito.when(userRepo.findByCpf(Mockito.anyString())).thenReturn(new User());
		assertNotNull(service.findByCpf("000.000.000-01"));
	}

	@Test(priority = 2)
	public void findAllUsers() {
		Mockito.when(userRepo.findAllByOrderByNameAsc()).thenReturn(Collections.emptyList());
		assertNotNull(service.findAllUsers());
	}

	@Test(priority = 3)
	public void findProfiles() {
		Mockito.when(profileRepo.findAllByOrderByNameAsc()).thenReturn(Collections.emptyList());
		assertNotNull(service.findProfiles());
	}

	@Test(priority = 4)
	public void loadUserByUsername() {
		Mockito.when(userRepo.findByCpf(Mockito.anyString())).thenReturn(new User());
		assertTrue(service.loadUserByUsername("ANY-CPF-HERE") instanceof StarWarsUserDetail);

		Mockito.when(userRepo.findByCpf(Mockito.anyString())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("ANY-CPF-HERE"));
	}

	@Test(priority = 5)
	public void findMovieCharacterById() {
		// test nonexistent moviecharacter
		MovieCharacter character = service.findMovieCharacterById(Integer.MAX_VALUE, true);
		assertNull(character);

		// find Darth Vader Character with her movies (id = 4)
		character = service.findMovieCharacterById(4, true);
		assertNotNull(character);
		assertEquals("Darth Vader", character.getName());
		assertNotNull(character.getBirthYear());
		assertFalse(character.getMovies().isEmpty());

		// find Darth Vader Character without her movies (id = 4)
		character = service.findMovieCharacterById(4, false);
		assertNotNull(character);
		assertTrue(character.getMovies().isEmpty());
	}

	@Test(priority=6)
	public void findHumanMovieCharacter() {
		Specie specie = service.findHumanMovieCharacter();
		assertNotNull(specie);
		assertEquals("Human", specie.getName());
		assertEquals(35, specie.getPeoples().size());
	}
}
