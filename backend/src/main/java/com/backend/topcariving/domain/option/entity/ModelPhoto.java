package com.backend.topcariving.domain.option.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Table("MODEL_PHOTO")
@AllArgsConstructor
@Getter
public class ModelPhoto {

	@Id
	private Long modelPhotoId;

	private String content;

	private String photoSvgUrl;

	private String photoPngUrl;

	// FK 값
	private Long carOptionId;
}
