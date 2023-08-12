package com.backend.topcariving.domain.option.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelPhoto {

	private Long modelPhotoId;

	private String content;

	private String photoSvgUrl;

	private String photoPngUrl;

	// FK 값
	private Long carOptionId;
}
