package com.backend.topcariving.domain.archive.dto;

import java.util.List;

import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TotalReviewDTO {

	private String review;
	private List<TagResponseDTO> tags;
}
