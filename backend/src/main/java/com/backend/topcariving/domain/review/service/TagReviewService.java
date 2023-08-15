package com.backend.topcariving.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.review.repository.TagReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagReviewService {

	private final TagReviewRepository tagReviewRepository;

	public List<TagResponseDTO> getTagResponseDTOs(MyCar myCar) {
		return tagReviewRepository.findTagResponseDTOByMyCarId(myCar.getMyCarId());
	}
}
