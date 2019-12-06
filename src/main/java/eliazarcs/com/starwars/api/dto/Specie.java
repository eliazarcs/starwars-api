package eliazarcs.com.starwars.api.dto;

import java.util.ArrayList;
import java.util.List;

public class Specie {
	private String name;
	private Float weightAverage;
	private final List<String> peoples;
	
	public Specie() {
		peoples = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getWeightAverage() {
		return weightAverage;
	}

	public List<String> getPeoples() {
		return peoples;
	}

	public void setWeightAverage(Float weightAverage) {
		this.weightAverage = weightAverage;
	}
}
