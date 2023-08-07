package com.backend.topcariving.domain.bookmark.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Table("BOOKMARK")
public class Bookmark {

	@Id
	private Long bookmarkId;

	private Boolean isAlive;

	// FK
	private Long userId;

	private Long archivingId;
}
