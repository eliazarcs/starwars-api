package eliazarcs.com.starwars.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eliazarcs.com.starwars.api.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
	Profile findByName(String name);
	List<Profile> findAllByOrderByNameAsc();
}
