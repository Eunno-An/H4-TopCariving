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
			.allowedOrigins("http://localhost:5173", "https://www.topcariving.com", "https://dev.topcariving.com", "https://test.topcariving.com", "http://localhost:8080")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("Authorization", "Content-type", "Location", "Accept", "Origin", "Host", "Referer", "Cache-Control", "Connection")
			.allowCredentials(true)
			.maxAge(MAX_AGE_SECS);
	}

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginArgumentResolver(tokenProvider));
	}

}
