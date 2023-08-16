package com.backend.topcariving.global.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.topcariving.global.auth.service.TokenProvider;
import com.backend.topcariving.global.auth.filter.LoginFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

	private final TokenProvider tokenProvider;

	@Bean
	public FilterRegistrationBean<Filter> addFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

		filterRegistrationBean.setFilter(new LoginFilter(tokenProvider));
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/api/*");

		return filterRegistrationBean;
	}
}
