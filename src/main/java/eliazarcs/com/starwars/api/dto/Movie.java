package eliazarcs.com.starwars.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Movie {
	private Integer id;
	private String name;

	public Movie() {
	}
	
	public Movie(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
