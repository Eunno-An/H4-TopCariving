package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.entity.CarArchiving;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.engine.EngineResponseDTO;
import com.backend.topcariving.domain.option.dto.model.ModelPhotoDTO;
import com.backend.topcariving.domain.option.dto.model.ModelResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.EngineDetail;
import com.backend.topcariving.domain.option.entity.ModelPhoto;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.option.repository.EngineDetailRepository;
import com.backend.topcariving.domain.option.repository.ModelPhotoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrimService {

	private static final String MODEL = "모델";
	private static final String ENGINE = "엔진";

	private final CarOptionRepository carOptionRepository;
	private final ModelPhotoRepository modelPhotoRepository;
	private final CarArchivingRepository carArchivingRepository;
	private final MyCarRepository myCarRepository;
	private final EngineDetailRepository engineDetailRepository;

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

	public Long saveModel(Long userId, Long carOptionId) {
		verifyCarOptionId(MODEL, carOptionId);

		CarArchiving carArchiving = CarArchiving.builder()
										.userId(userId)
										.isComplete(false)
										.isAlive(true)
										.build();
		carArchiving = carArchivingRepository.save(carArchiving);

		MyCar mycar = MyCar.builder()
			.archivingId(carArchiving.getArchivingId())
			.carOptionId(carOptionId)
			.build();

		myCarRepository.save(mycar);

		return carArchiving.getArchivingId();
	}

	@Transactional(readOnly = true)
	public List<EngineResponseDTO> getEngines() {
		List<CarOption> engines = carOptionRepository.findByCategoryDetail(ENGINE);

		return engines.stream()
			.map(this::getEngineResponseDTO).collect(Collectors.toList());

	}

	private EngineResponseDTO getEngineResponseDTO(final CarOption engine) {
		final EngineDetail engineDetail = engineDetailRepository.findByCarOptionId(engine.getCarOptionId())
			.orElseThrow(InvalidCarOptionIdException::new);
		return EngineResponseDTO.of(engine, engineDetail);
	}

	public Long saveEngine(Long userId, Long carOptionId, Long archivingId) {
		verifyCarArchiving(userId, archivingId);
		verifyCarOptionId(ENGINE, carOptionId);

		MyCar myCar = MyCar.builder()
			.carOptionId(carOptionId)
			.archivingId(archivingId)
			.build();

		myCarRepository.save(myCar);
		return archivingId;
	}

	private void verifyCarArchiving(Long userId, Long archivingId) {
		if (!carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId)) {
			throw new InvalidAuthorityException();
		}
	}

	private void verifyCarOptionId(String category, Long carOptionId) {
		if (!carOptionRepository.existsByCarOptionIdAndCategoryDetail(carOptionId, category)) {
			throw new InvalidCarOptionIdException();
		}
	}
}
