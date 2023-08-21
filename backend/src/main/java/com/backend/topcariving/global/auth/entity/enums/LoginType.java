package com.backend.topcariving.global.auth.entity.enums;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoginType {

	LOCAL("local"),
	HYUNDAI("hyundai");

	private final String name;

	LoginType(final String name) {
		this.name = name;
	}

	public static LoginType valueOfName(String name) {
		return Arrays.stream(LoginType.values())
			.filter(loginType -> Objects.equals(name, loginType.getName()))
			.findFirst()
			.orElse(LOCAL);
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
