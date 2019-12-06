package eliazarcs.com.starwars.api.infrastructure.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import eliazarcs.com.starwars.api.model.User;

public class StarWarsUserDetail implements UserDetails{
	private User user;
	
	public StarWarsUserDetail(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(user != null) {
			GrantedAuthority role = new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return user.getProfile().getName();
				}
			};
			List<GrantedAuthority> grants = new ArrayList<GrantedAuthority>();
			grants.add(role);
			return grants;
		}
		return null;
	}

	@Override
	public String getPassword() {
		return user.getPwd();
	}

	@Override
	public String getUsername() {
		return user.getCpf();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
