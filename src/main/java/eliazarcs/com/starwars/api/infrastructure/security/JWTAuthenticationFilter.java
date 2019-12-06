package eliazarcs.com.starwars.api.infrastructure.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import eliazarcs.com.starwars.api.util.StarWarsUtil;
import io.jsonwebtoken.JwtException;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		JWTAuthenticationService authenticationService = StarWarsUtil.getBean(JWTAuthenticationService.class);
		try {
			Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JwtException e) {
			StarWarsExceptionHandler.handle((HttpServletRequest) request, (HttpServletResponse) response, e);
		} catch (Exception e) {
			StarWarsExceptionHandler.handle((HttpServletRequest) request, (HttpServletResponse) response, e);
		} finally {
			filterChain.doFilter(request, response);
		}
	}
}
