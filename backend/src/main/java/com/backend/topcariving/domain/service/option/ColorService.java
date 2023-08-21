package com.backend.topcariving.domain.service.option;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.dto.option.response.color.BothColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.InteriorColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.repository.option.CarOptionRepository;
import com.backend.topcariving.domain.repository.review.TagReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ColorService {

	private final TagReviewRepository tagReviewRepository;
	private final CarOptionRepository carOptionRepository;

	private static final int TAG_LIMIT = 5;

	public List<ExteriorColorResponseDTO> getExteriorColors() {
		final List<CarOption> carOptions = carOptionRepository.findByCategoryDetail(
			CategoryDetail.EXTERIOR_COLOR);

		return carOptions.stream()
			.map(carOption -> {
				final List<TagResponseDTO> tagResponseDTO = tagReviewRepository.findTagResponseDTOByCarOptionIdAndLimit(
					carOption.getCarOptionId(), TAG_LIMIT);
				return ExteriorColorResponseDTO.of(carOption, tagResponseDTO);
			}).collect(Collectors.toList());
	}

	public List<InteriorColorResponseDTO> getInteriorColors() {
		final List<CarOption> carOptions = carOptionRepository.findByCategoryDetail(
			CategoryDetail.INTERIOR_COLOR);

		return carOptions.stream()
			.map(carOption -> {
				final List<TagResponseDTO> tagResponseDTO = tagReviewRepository.findTagResponseDTOByCarOptionIdAndLimit(
					carOption.getCarOptionId(), TAG_LIMIT);
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
