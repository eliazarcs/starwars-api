package eliazarcs.com.starwars.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "O atributo Name é obrigatório")
	private String name;
	@NotNull(message = "O atributo Profile é obrigatório")
	@ManyToOne()
	private Profile profile;
	@NotBlank(message = "O atributo CPF é obrigatório")
	private String cpf;

	public User() {
		super();
	}
	
	public User(String name, Profile profile, String cpf) {
		super();
		this.name = name;
		this.profile = profile;
		this.cpf = cpf;
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
