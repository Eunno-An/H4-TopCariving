package com.backend.topcariving.global.auth.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import com.backend.topcariving.global.auth.exception.InvalidTokenException;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.backend.topcariving.global.dto.FailureResponse;
import com.backend.topcariving.global.exception.ExceptionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter implements Filter {

	private final List<String> whiteList = List.of("/api/options/colors/");

	private final TokenProvider tokenProvider;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws
		IOException,
		ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		if (whiteList.contains(servletRequest.getServletPath()))
			chain.doFilter(request, response);
		try {
			verifyToken(servletRequest);
			chain.doFilter(request, response);
		} catch (JwtException | InvalidTokenException e) {
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			FailureResponse failureResponse = new FailureResponse(ExceptionStatus.INVALID_TOKEN);
			ObjectMapper mapper = new ObjectMapper();
			PrintWriter writer = servletResponse.getWriter();
			writer.write(mapper.writeValueAsString(failureResponse));
		}
	}

	private void verifyToken(HttpServletRequest request) {
		final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.hasText(bearerToken)) {
			throw new InvalidTokenException();
		}

		final String accessToken = tokenProvider.extractToken(bearerToken);
		tokenProvider.verifyToken(accessToken);
	}
}
