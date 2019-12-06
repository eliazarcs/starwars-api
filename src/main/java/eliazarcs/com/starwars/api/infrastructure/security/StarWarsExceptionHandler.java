package eliazarcs.com.starwars.api.infrastructure.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eliazarcs.com.starwars.api.dto.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Component
public class StarWarsExceptionHandler implements AccessDeniedHandler {

	public static void handle(final HttpServletRequest request, final HttpServletResponse response,
			final Exception exception) throws IOException {
		handle(response, exception);
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		handle(response, accessDeniedException);
	}

	private static void handle(final HttpServletResponse response, final Exception exception)
			throws IOException, JsonGenerationException, JsonMappingException {
		ApiResponse resp = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Uma falha não prevista ocorreu. Contacte o Administrador da API");
		if (exception instanceof ExpiredJwtException) {
			resp = new ApiResponse(HttpStatus.FORBIDDEN.value(), "O token JWT expirou.");
		} else if (exception instanceof AccessDeniedException) {
			resp = new ApiResponse(HttpStatus.FORBIDDEN.value(), "Usuário não tem permissão de acessar este serviço");
		} else if (exception instanceof JwtException || exception instanceof AuthenticationException) {
			resp = new ApiResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
		}
		OutputStream out = response.getOutputStream();
		response.setStatus(resp.getStatus());
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, resp);
	}

}
