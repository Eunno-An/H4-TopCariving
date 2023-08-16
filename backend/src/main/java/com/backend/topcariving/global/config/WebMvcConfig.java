package com.backend.topcariving.global.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.backend.topcariving.global.auth.resolver.LoginArgumentResolver;
import com.backend.topcariving.global.auth.service.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final TokenProvider tokenProvider;
	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginArgumentResolver(tokenProvider));
	}

}
