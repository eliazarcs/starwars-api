package eliazarcs.com.starwars.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eliazarcs.com.starwars.api.dto.Movie;
import eliazarcs.com.starwars.api.dto.MovieCharacter;
import eliazarcs.com.starwars.api.dto.Specie;
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
	@Value("${url.people}")
	private String urlPeople;
	@Value("${url.movies}")
	private String urlMovie;
	@Value("${url.human.species}")
	private String urlHumanSpecies;
	private ObjectMapper mapper = new ObjectMapper();

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
		if (user == null) {
			throw new UsernameNotFoundException(cpf);
		}
		return new StarWarsUserDetail(user);
	}

	public void saveDefaultUsers() {
		List<Profile> pfs = profileRepo.findAllByOrderByNameAsc();
		AtomicInteger count = new AtomicInteger(0);
		final String[] names = new String[] { "Vader", "Yoda" };
		pfs.stream().forEach(pf -> {
			final User newUser = new User(names[count.get()], pf, "000.000.000-0" + count.incrementAndGet(),
					pwdEncoder.encode("123"));
			userRepo.save(newUser);
		});
	}

	public MovieCharacter findMovieCharacterById(Integer id, boolean requiredMovies) {
		if (id != null) {
			ResponseEntity<String> response = accessSWAPI(urlPeople + id);
			if (response.getStatusCode() == HttpStatus.OK) {
				JsonNode root;
				MovieCharacter character = new MovieCharacter();
				try {
					root = mapper.readTree(response.getBody());
					character.setName(root.get("name").asText());
					character.setBirthYear(root.get("birth_year").asText());
					try {
						// Alguns personagens não tem peso (ex: https://swapi.co/api/people/12/)
						character.setWeight(Float.valueOf(root.get("mass").asText()));
					}catch (Exception e) {
						character.setWeight(0F);
					}
					if(requiredMovies) {
						getCharacterMovies(root, character);
					}
					return character;
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			} else {
				return null;
			}
		}
		return null;
	}

	private void getCharacterMovies(JsonNode root, final MovieCharacter character) {
		Integer idMovie;
		Movie movie;
		for (JsonNode node : root.withArray("films")) {
			idMovie = (Integer.valueOf(node.asText().split("/")[5]));
			movie = findMovieById(idMovie);
			if(movie != null) {
				character.getMovies().add(movie.getName());
			}
		}
	}

	private ResponseEntity<String> accessSWAPI(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response;
	}

	private Movie findMovieById(Integer id) {
		ResponseEntity<String> response = accessSWAPI(urlMovie + id);
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			try {
				String mvName = mapper.readTree(response.getBody()).get("title").asText();
				return new Movie(id, mvName);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private <T> List<T> iterableToList(Iterable<T> it) {
		List<T> list = new ArrayList<T>();
		it.forEach(list::add);
		return list;
	}

	public Specie findHumanMovieCharacter() {
		ResponseEntity<String> response = accessSWAPI(urlHumanSpecies);
		if (response.getStatusCode() == HttpStatus.OK) {
			JsonNode root;
			Specie specie = new Specie();
			try {
				root = mapper.readTree(response.getBody());
				specie.setName(root.get("name").asText());
				specie.setWeightAverage(Float.valueOf(root.get("average_height").asText()));
				getPeopleFromSpecie(root, specie);
				return specie;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void getPeopleFromSpecie(JsonNode root, final Specie specie) {
		Integer idHuman;
		Float average = 0F;
		Integer totalHuman = 0;
		MovieCharacter human;
		for (JsonNode node : root.withArray("people")) {
			idHuman = (Integer.valueOf(node.asText().split("/")[5]));
			human = findMovieCharacterById(idHuman, false);
			if (human != null) {
				specie.getPeoples().add(human.getName());
				average += human.getWeight();
				totalHuman++;
			}
		}
		if (totalHuman > 0) {
			specie.setWeightAverage(average / totalHuman);
		}else {
			specie.setWeightAverage(0f);
		}
	}
}
