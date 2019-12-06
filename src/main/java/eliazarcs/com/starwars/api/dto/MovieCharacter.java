package eliazarcs.com.starwars.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MovieCharacter {
	@JsonIgnore
	private Integer id;
	private String name;
	private String birthYear;
	private Float weight;
	private final List<String> movies;

	public MovieCharacter() {
		movies = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public List<String> getMovies() {
		return movies;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}
}
