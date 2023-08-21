package com.backend.topcariving.domain.service.option;

import static com.backend.topcariving.domain.entity.archive.enums.ArchivingType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.entity.archive.MyCar;
import com.backend.topcariving.domain.repository.archive.CarArchivingRepository;
import com.backend.topcariving.domain.repository.archive.MyCarRepository;
import com.backend.topcariving.domain.dto.option.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.dto.option.response.engine.EngineResponseDTO;
import com.backend.topcariving.domain.dto.option.response.model.ModelPhotoDTO;
import com.backend.topcariving.domain.dto.option.response.model.ModelResponseDTO;
import com.backend.topcariving.domain.dto.option.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.entity.option.EngineDetail;
import com.backend.topcariving.domain.entity.option.ModelPhoto;
import com.backend.topcariving.domain.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.repository.option.CarOptionRepository;
import com.backend.topcariving.domain.repository.option.EngineDetailRepository;
import com.backend.topcariving.domain.repository.option.ModelPhotoRepository;
import com.backend.topcariving.global.utils.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrimService {

	private final CarOptionRepository carOptionRepository;
	private final ModelPhotoRepository modelPhotoRepository;
	private final CarArchivingRepository carArchivingRepository;
	private final MyCarRepository myCarRepository;
	private final EngineDetailRepository engineDetailRepository;

	private final Validator validator;

	public List<ModelResponseDTO> getModels() {
		List<CarOption> options = carOptionRepository.findByCategoryDetail(CategoryDetail.MODEL);

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

	@Transactional
	public Long saveModel(Long userId, SelectOptionRequestDTO selectOptionRequestDTO) {
		Long carOptionId = selectOptionRequestDTO.getCarOptionId();
		Long archivingId = selectOptionRequestDTO.getArchivingId();

		validator.verifyCarOptionId(CategoryDetail.MODEL, carOptionId);

		if (archivingId != null) {
			return updateModel(userId, carOptionId, archivingId);
		}

		CarArchiving carArchiving = CarArchiving.builder()
			.userId(userId)
			.isComplete(false)
			.isAlive(true)
			.archivingType(MAKE)
			.build();
		carArchiving = carArchivingRepository.save(carArchiving);

		MyCar mycar = MyCar.builder()
			.archivingId(carArchiving.getArchivingId())
			.carOptionId(carOptionId)
			.build();

		myCarRepository.save(mycar);

		return carArchiving.getArchivingId();
	}

	private Long updateModel(Long userId, Long carOptionId, Long archivingId) {
		validator.verifyCarArchiving(userId, archivingId);

		myCarRepository.updateCarOptionIdByArchivingIdAndCategoryDetail(archivingId, carOptionId, CategoryDetail.MODEL);
		return archivingId;
	}

	public List<EngineResponseDTO> getEngines() {
		List<CarOption> engines = carOptionRepository.findByCategoryDetail(CategoryDetail.ENGINE);

		return engines.stream()
			.map(this::getEngineResponseDTO).collect(Collectors.toList());

	}

	private EngineResponseDTO getEngineResponseDTO(final CarOption engine) {
		final EngineDetail engineDetail = engineDetailRepository.findByCarOptionId(engine.getCarOptionId())
			.orElseThrow(InvalidCarOptionIdException::new);
		return EngineResponseDTO.of(engine, engineDetail);
	}

	public List<OptionResponseDTO> getOptions(CategoryDetail categoryDetail) {
		final List<CarOption> carOptions = carOptionRepository.findByCategoryDetail(categoryDetail);

		return carOptions.stream()
			.map(OptionResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public Long saveOption(Long userId, SelectOptionRequestDTO selectOptionRequestDTO, CategoryDetail categoryDetail) {
		Long carOptionId = selectOptionRequestDTO.getCarOptionId();
		Long archivingId = selectOptionRequestDTO.getArchivingId();

		validator.verifyCarArchiving(userId, archivingId);
		validator.verifyCarOptionId(categoryDetail, carOptionId);

		MyCar myCar = MyCar.builder()
			.carOptionId(carOptionId)
			.archivingId(archivingId)
			.build();

		myCarRepository.deleteByArchivingIdAndCategoryDetail(archivingId, categoryDetail);
		myCarRepository.save(myCar);

		return archivingId;
	}

}
