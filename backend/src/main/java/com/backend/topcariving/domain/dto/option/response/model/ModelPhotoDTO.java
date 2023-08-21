package com.backend.topcariving.domain.dto.option.response.model;

import com.backend.topcariving.domain.entity.option.ModelPhoto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "트림의 사진 파일에 대한 SVG, PNG 경로")
public class ModelPhotoDTO {

	@Schema(description = "photo에 대한 설명")
	private String content;

	@Schema(description = "svg에 대한 파일 경로")
	private String photoSvgUrl;

	@Schema(description = "png에 대한 파일 경로")
	private String photoPngUrl;

	public static ModelPhotoDTO from(ModelPhoto modelPhoto) {
		return ModelPhotoDTO.builder()
			.content(modelPhoto.getContent())
			.photoPngUrl(modelPhoto.getPhotoPngUrl())
			.photoSvgUrl(modelPhoto.getPhotoSvgUrl())
			.build();
	}
}
