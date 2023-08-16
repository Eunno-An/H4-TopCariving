package com.backend.topcariving.global.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

import com.backend.topcariving.global.auth.service.TokenProvider;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter implements Filter {

	private final TokenProvider tokenProvider;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws
		IOException,
		ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		try {
			verifyToken(servletRequest);
			chain.doFilter(request, response);
		} catch (JwtException e) {
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private void verifyToken(HttpServletRequest request) {
		final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String accessToken = tokenProvider.extractToken(bearerToken);
		tokenProvider.verifyToken(accessToken);
	}
}
