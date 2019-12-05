package eliazarcs.com.starwars.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eliazarcs.com.starwars.api.model.Profile;
import eliazarcs.com.starwars.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findAllByOrderByNameAsc();
}
