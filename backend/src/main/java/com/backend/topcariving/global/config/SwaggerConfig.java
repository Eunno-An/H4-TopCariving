package com.backend.topcariving.global.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	private static final String DEV_URL = "http://dev.topcariving.com:8080";
	private static final String LOCAL_URL = "http://localhost:8080";

	@Bean
	public OpenAPI openAPI() {

		Server devServer = new Server();
		devServer.setDescription("dev");
		devServer.setUrl(DEV_URL);

		Server localServer = new Server();
		localServer.setDescription("local");
		localServer.setUrl(LOCAL_URL);

		return new OpenAPI()
			.info(getInfo())
			.servers(Arrays.asList(devServer, localServer));

	}

	private Info getInfo() {
		return new Info()
			.title("TopCariving API")
			.description("TopCariving API DOCS");
	}
}
