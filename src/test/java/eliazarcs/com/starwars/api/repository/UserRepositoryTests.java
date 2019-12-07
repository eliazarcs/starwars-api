package eliazarcs.com.starwars.api.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eliazarcs.com.starwars.api.Application;
import eliazarcs.com.starwars.api.model.User;

@SpringBootTest(classes = Application.class)
public class UserRepositoryTests extends AbstractTestNGSpringContextTests {
	@Autowired
	private UserRepository repository;

	@Test(priority=1)
	public void findAllByOrderByNameAsc_defaultUsersTest() {
		List<User> users = repository.findAllByOrderByNameAsc();
		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals("Vader", users.get(0).getName());
	}
	
	@Test(priority=2)
	public void findByCpf() {
		User user = repository.findByCpf("000.000.000-01");
		assertNotNull(user);
		assertEquals("000.000.000-01", user.getCpf());
		
		user = repository.findByCpf("NOT_EXIST");
		assertNull(user);
	}
	
	@Test(priority=3)
	public void findAllByOrderByNameAsc_noUsers() {
		repository.deleteAll();
		List<User> users = repository.findAllByOrderByNameAsc();
		assertTrue(users.isEmpty());
	}
}
