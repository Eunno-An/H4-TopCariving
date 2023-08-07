package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.option.dto.model.ModelPhotoDTO;
import com.backend.topcariving.domain.option.dto.model.ModelResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.ModelPhoto;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.option.repository.ModelPhotoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrimService {

	private static final String MODEL = "모델";

	private final CarOptionRepository carOptionRepository;
	private final ModelPhotoRepository modelPhotoRepository;

	@Transactional(readOnly = true)
	public List<ModelResponseDTO> getModels() {
		List<CarOption> options = carOptionRepository.findByCategoryDetail(MODEL);

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
