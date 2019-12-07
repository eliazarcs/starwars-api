package eliazarcs.com.starwars.api.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eliazarcs.com.starwars.api.Application;
import eliazarcs.com.starwars.api.model.Profile;
import eliazarcs.com.starwars.api.model.User;

@SpringBootTest(classes = Application.class)
public class ProfileRepositoryTests extends AbstractTestNGSpringContextTests {
	@Autowired
	private ProfileRepository repository;
	
	@Test(priority=1)
	public void findByName() {
		Profile pf = repository.findByName("Administrador");
		assertNotNull(pf);
		assertEquals(pf.getName(), "Administrador");
	}
	
	@Test(priority=2)
	public void findAllByOrderByNameAsc_defaultProfileTest() {
		List<Profile> pfs = repository.findAllByOrderByNameAsc();
		assertNotNull(pfs);
		assertEquals(2, pfs.size());
		assertEquals("Administrador", pfs.get(0).getName());
	}	
}
