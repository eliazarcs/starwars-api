package eliazarcs.com.starwars.api.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import eliazarcs.com.starwars.api.service.StarWarsService;

@Configuration
@EnableWebSecurity
public class StarWarsSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private StarWarsService userDetailService;
	@Autowired
	private StarWarsExceptionHandler exceptionHandler;
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return userDetailService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
			.antMatchers("/authentication", "/swagger-ui.html", "/swagger-resources/**","/v2/api-docs", "/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET, "/users").hasAuthority("Administrador")
				.antMatchers(HttpMethod.GET, "/profiles").hasAuthority("Administrador").anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedHandler(exceptionHandler).and()
				.addFilterBefore(new JWTLoginFilter("/authentication", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
