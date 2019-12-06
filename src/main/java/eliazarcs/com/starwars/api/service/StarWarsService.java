package eliazarcs.com.starwars.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eliazarcs.com.starwars.api.infrastructure.security.StarWarsUserDetail;
import eliazarcs.com.starwars.api.model.Profile;
import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.repository.ProfileRepository;
import eliazarcs.com.starwars.api.repository.UserRepository;

@Service
public class StarWarsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProfileRepository profileRepo;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	public User saveUser(User user) {
		Profile pf = profileRepo.findByName(user.getProfile().getName());
		user.setProfile(pf);
		return userRepo.save(user);
	}

	public User findByCpf(String cpf) {
		return userRepo.findByCpf(cpf);
	}
	
	public List<User> findAllUsers() {
		return iterableToList(userRepo.findAllByOrderByNameAsc());
	}
	
	public List<Profile> findProfiles() {
		return iterableToList(profileRepo.findAllByOrderByNameAsc());
	}
	
	public List<Profile> saveDefaultProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		
		profiles.add(new Profile("Usuario"));
		profiles.add(new Profile("Administrador"));
		return iterableToList(profileRepo.saveAll(profiles));
	}
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		User user = userRepo.findByCpf(cpf);
		if(user == null) {
			throw new UsernameNotFoundException(cpf);
		}		
		return new StarWarsUserDetail(user);
	}
	
	public void saveDefaultUsers() {
		List<Profile> pfs = profileRepo.findAllByOrderByNameAsc();
		AtomicInteger count= new AtomicInteger(0);
		final String[] names = new String[]{"Vader", "Yoda"};
		pfs.stream().forEach(pf -> {
			final User newUser = new User(names[count.get()], pf, "000.000.000-0" + count.incrementAndGet(), pwdEncoder.encode("123"));
			userRepo.save(newUser);
		});
	}
	
	private <T> List<T> iterableToList(Iterable<T> it) {
		List<T> list = new ArrayList<T>();
		it.forEach(list::add);
		return list;
	}
}
