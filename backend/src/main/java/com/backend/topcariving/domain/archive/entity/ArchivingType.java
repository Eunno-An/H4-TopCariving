package com.backend.topcariving.domain.archive.entity;

import lombok.Getter;

@Getter

public enum ArchivingType {
	MAKE("내 차 만들기"),
	BUY("구매"),
	DRIVE("시승");

	private final String type;

	ArchivingType(final String type) {
		this.type = type;
	}

}
