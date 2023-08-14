package com.backend.topcariving.domain.bookmark.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Bookmark {

	private Long bookmarkId;

	private Boolean isAlive;

	// FK
	private Long userId;

	private Long archivingId;
}
