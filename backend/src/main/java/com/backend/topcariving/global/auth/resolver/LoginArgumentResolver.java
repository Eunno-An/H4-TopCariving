package com.backend.topcariving.global.auth.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.backend.topcariving.global.auth.annotation.Login;
import com.backend.topcariving.global.auth.service.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private final TokenProvider tokenProvider;

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		final boolean isExistAnnotation = parameter.hasParameterAnnotation(Login.class);
		final boolean hasUserIdType = Long.class.isAssignableFrom(parameter.getParameterType());

		return isExistAnnotation && hasUserIdType;
	}

	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String token = tokenProvider.extractToken(bearerToken);
		return tokenProvider.getUserIdFromToken(token);
	}
}
