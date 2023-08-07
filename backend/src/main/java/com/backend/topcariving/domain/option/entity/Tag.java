package com.backend.topcariving.domain.option.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Table("TAG")
@AllArgsConstructor
@Getter
public class Tag {

	@Id
	private Long tagId;

	private String tagText;
}
