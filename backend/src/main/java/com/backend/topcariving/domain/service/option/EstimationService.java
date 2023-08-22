package com.backend.topcariving.domain.service.option;

import static com.backend.topcariving.domain.entity.option.enums.Category.*;
import static com.backend.topcariving.domain.entity.option.enums.ExteriorColor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.dto.option.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.dto.option.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.dto.option.response.estimation.SummaryResponseDTO;
import com.backend.topcariving.domain.entity.archive.MyCar;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.repository.archive.MyCarRepository;
import com.backend.topcariving.domain.repository.option.CarOptionRepository;
import com.backend.topcariving.global.utils.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimationService {

	private final MyCarRepository myCarRepository;
	private final CarOptionRepository carOptionRepository;

	private final Validator validator;

	public SummaryResponseDTO summary(Long userId, Long archivingId) {
		validator.verifyCarArchiving(userId, archivingId);

		final List<OptionSummaryDTO> optionSummaryDTOs = myCarRepository.findOptionSummaryByArchivingId(
			archivingId);

		CarOption colorOption = carOptionRepository.findByArchivingIdAndCategoryDetail(archivingId, CategoryDetail.EXTERIOR_COLOR)
			.orElseThrow(InvalidArchivingIdException::new);
		String photoUrl = getPhotoUrl(colorOption.getCarOptionId());

		Map<String, List<OptionSummaryDTO>> result = convertToOptionSummaryMap(
			optionSummaryDTOs);

		return new SummaryResponseDTO(photoUrl, result);
	}

	@Transactional
	public Long changeOptions(Long userId, EstimationChangeRequestDTO estimationChangeRequestDTO) {
		final Long archivingId = estimationChangeRequestDTO.getArchivingId();
		final List<Long> optionIds = estimationChangeRequestDTO.getOptionIds();

		validator.verifyCarArchiving(userId, archivingId);

		final List<CarOption> carOptions = carOptionRepository.findByIds(optionIds);
		final CarOption carOption = carOptions.get(0);
		validator.verifySameCategory(carOptions, carOption.getCategoryDetail());

		myCarRepository.deleteByArchivingIdAndCategoryDetail(archivingId, carOption.getCategoryDetail());

		final List<MyCar> myCars = optionIds.stream()
			.map(carOptionId -> MyCar.builder()
				.carOptionId(carOptionId)
				.archivingId(archivingId).build())
			.collect(Collectors.toList());
		myCarRepository.saveMultipleData(myCars);

		return archivingId;
	}

	private Map<String, List<OptionSummaryDTO>> convertToOptionSummaryMap(
		final List<OptionSummaryDTO> optionSummaryDTOs) {
		Map<String, List<OptionSummaryDTO>> result = new HashMap<>();

		for (OptionSummaryDTO optionSummaryDTO : optionSummaryDTOs) {
			String key = Category.findCarOptionResultKey(optionSummaryDTO.getCategoryDetail(), optionSummaryDTO.getCategory());
			if (!result.containsKey(key)) {
				result.put(key, new ArrayList<>());
			}

			savedSummaryValues(result, optionSummaryDTO, key);
		}
		return result;
	}

	private void savedSummaryValues(Map<String, List<OptionSummaryDTO>> result, OptionSummaryDTO optionSummaryDTO, String key) {
		if (Objects.equals(key, CHOICE.getName())) {
			List<String> childOptions = carOptionRepository.findStringByParentOptionId(
				optionSummaryDTO.getCarOptionId());
			optionSummaryDTO.setChildOptions(childOptions);
		}

		List<OptionSummaryDTO> values = result.get(key);
		values.add(optionSummaryDTO);
	}

}
