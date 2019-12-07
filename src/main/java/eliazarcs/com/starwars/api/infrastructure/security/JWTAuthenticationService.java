package eliazarcs.com.starwars.api.infrastructure.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import eliazarcs.com.starwars.api.model.User;
import eliazarcs.com.starwars.api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTAuthenticationService {
	@Value(value = "${jwt.expiration.ms}")
	private Long expirationTime;
	@Value(value = "${jwt.secret}")
	private String secret;
	@Value(value = "${jwt.prefix}")
	private String tokenPrefix;
	@Value(value = "${jwt.header}")
	private String authorizationHeaderParam;
	@Autowired
	private UserRepository userRepository;

	public void addAuthentication(HttpServletResponse response, String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

		response.addHeader(authorizationHeaderParam, tokenPrefix + " " + JWT);
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(authorizationHeaderParam);

		if (token != null) {
			String cpf = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace(tokenPrefix, "")).getBody()
					.getSubject();

			if (cpf != null) {
				User autheticatedUser = userRepository.findByCpf(cpf);
				List l = new ArrayList<>();
				l.add(new SimpleGrantedAuthority(autheticatedUser.getProfile().getName()));
				return new UsernamePasswordAuthenticationToken(cpf, null, l);
			}
		}
		return null;
	}
}
