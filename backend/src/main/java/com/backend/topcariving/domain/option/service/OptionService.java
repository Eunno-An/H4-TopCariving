package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.option.dto.ModelPhotoDTO;
import com.backend.topcariving.domain.option.dto.ModelResponseDTO;
import com.backend.topcariving.domain.option.entity.ModelPhoto;
import com.backend.topcariving.domain.option.entity.Option;
import com.backend.topcariving.domain.option.repository.ModelPhotoRepository;
import com.backend.topcariving.domain.option.repository.OptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionService {

	private static final String MODEL = "모델";

	private final OptionRepository optionRepository;
	private final ModelPhotoRepository modelPhotoRepository;

	@Transactional(readOnly = true)
	public List<ModelResponseDTO> getModels() {
		List<Option> options = optionRepository.findByCategoryDetail(MODEL);

		return options.stream()
			.map(option -> ModelResponseDTO.of(option, getPageOptions(option.getCarOptionId())))
			.collect(Collectors.toList());
	}

	private List<ModelPhotoDTO> getPageOptions(Long carOptionId) {
		final List<ModelPhoto> modelPhotos = modelPhotoRepository.findAllByCarOptionId(carOptionId);

		return modelPhotos.stream()
			.map(ModelPhotoDTO::from)
			.collect(Collectors.toList());
	}
}
