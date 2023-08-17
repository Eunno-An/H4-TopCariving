package com.backend.topcariving.domain.user.entity;

import lombok.Getter;

@Getter
public enum LoginType {

	LOCAL("local"), HYUNDAI("hyundai");

	private final String type;

	LoginType(final String type) {
		this.type = type;
	}

}
