package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.option.dto.response.color.BothColorResponseDTO;
import com.backend.topcariving.domain.option.dto.response.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.option.dto.response.color.InteriorColorResponseDTO;
import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.review.repository.TagReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ColorService {

	private final TagReviewRepository tagReviewRepository;
	private final CarOptionRepository carOptionRepository;

	public List<ExteriorColorResponseDTO> getExteriorColors() {
		final List<CarOption> carOptions = carOptionRepository.findByCategoryDetail(
			CategoryDetail.EXTERIOR_COLOR.getName());

		return carOptions.stream()
			.map(carOption -> {
				final List<TagResponseDTO> tagResponseDTO = tagReviewRepository.findTagResponseDTOByCarOptionId(
					carOption.getCarOptionId());
				return ExteriorColorResponseDTO.of(carOption, tagResponseDTO);
			}).collect(Collectors.toList());
	}

	public List<InteriorColorResponseDTO> getInteriorColors() {
		final List<CarOption> carOptions = carOptionRepository.findByCategoryDetail(
			CategoryDetail.INTERIOR_COLOR.getName());

		return carOptions.stream()
			.map(carOption -> {
				final List<TagResponseDTO> tagResponseDTO = tagReviewRepository.findTagResponseDTOByCarOptionId(
					carOption.getCarOptionId());
				final List<CarOption> parent = carOptionRepository.findByParentOptionId(
					carOption.getCarOptionId());

				return InteriorColorResponseDTO.of(carOption, parent.get(0).getPhotoUrl(), tagResponseDTO);
			}).collect(Collectors.toList());
	}
	
	public BothColorResponseDTO getBothResponseDTO() {
		final List<ExteriorColorResponseDTO> exteriorColors = getExteriorColors();
		final List<InteriorColorResponseDTO> interiorColors = getInteriorColors();

		return new BothColorResponseDTO(exteriorColors, interiorColors);
	}
}
