package eliazarcs.com.starwars.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eliazarcs.com.starwars.api.model.Profile;
import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.repository.ProfileRepository;
import eliazarcs.com.starwars.api.repository.UserRepository;

@Service
public class StarWarsService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProfileRepository profileRepo;
	
	public User saveUser(User user) {
		Profile pf = profileRepo.findByName(user.getProfile().getName());
		user.setProfile(pf);
		return userRepo.save(user);
	}

	public List<User> findAllUsers() {
		return iterableToList(userRepo.findAllByOrderByNameAsc());
	}
	
	public List<Profile> findProfiles() {
		return iterableToList(profileRepo.findAllByOrderByNameAsc());
	}
	
	public List<Profile> saveDefaultProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		
		profiles.add(new Profile("Usuário"));
		profiles.add(new Profile("Administrador"));
		return iterableToList(profileRepo.saveAll(profiles));
	}
	
	private <T> List<T> iterableToList(Iterable<T> it) {
		List<T> list = new ArrayList<T>();
		it.forEach(list::add);
		return list;
	}
}
