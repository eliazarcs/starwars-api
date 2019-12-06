package eliazarcs.com.starwars.api.infrastructure.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.util.StarWarsUtil;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
		try {
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(credentials.getCpf(),
					credentials.getPwd(), Collections.emptyList()));
		} catch (Exception e) {
			StarWarsExceptionHandler.handle(request, response, e);
			return null;
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		JWTAuthenticationService authenticationService = StarWarsUtil.getBean(JWTAuthenticationService.class);
		authenticationService.addAuthentication(response, auth.getName());
	}
}
