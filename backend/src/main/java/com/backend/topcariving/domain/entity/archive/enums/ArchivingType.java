package com.backend.topcariving.domain.entity.archive.enums;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ArchivingType {
	MAKE("내 차 만들기"),
	BUY("구매"),
	DRIVE("시승"),
	EMPTY("empty");

	private final String name;

	ArchivingType(final String name) {
		this.name = name;
	}

	public static ArchivingType valueOfName(String name) {
		return Arrays.stream(ArchivingType.values())
			.filter(archivingType -> Objects.equals(name, archivingType.getName()))
			.findFirst()
			.orElse(EMPTY);
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
