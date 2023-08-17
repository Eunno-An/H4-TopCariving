package com.backend.topcariving.global.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@SecuritySchemes({
	@SecurityScheme(name = "Authorization",
	type = SecuritySchemeType.HTTP,
	description = "Bearer token",
	in = SecuritySchemeIn.HEADER,
	paramName = "Authorization",
	scheme = "bearer",
	bearerFormat = "JWT")
})
@Configuration
public class SwaggerConfig {

	private static final String DEV_URL = "https://dev.topcariving.com";
	private static final String TEST_URL = "http://test.topcariving.com:8080";
	private static final String LOCAL_URL = "http://localhost:8080";

	@Bean
	public OpenAPI openAPI() {

		Server devServer = new Server();
		devServer.setDescription("dev");
		devServer.setUrl(DEV_URL);

		Server testServer = new Server();
		testServer.setDescription("test");
		testServer.setUrl(TEST_URL);

		Server localServer = new Server();
		localServer.setDescription("local");
		localServer.setUrl(LOCAL_URL);

		return new OpenAPI()
			.info(getInfo())
			.servers(Arrays.asList(devServer, testServer, localServer));
	}

	private Info getInfo() {
		return new Info()
			.title("TopCariving API")
			.description("TopCariving API DOCS");
	}
}
