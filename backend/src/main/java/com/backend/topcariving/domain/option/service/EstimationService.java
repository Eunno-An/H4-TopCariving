package com.backend.topcariving.domain.option.service;

import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.option.dto.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.option.dto.response.estimation.SummaryResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.global.utils.Validator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimationService {

	private final CarArchivingRepository carArchivingRepository;
	private final MyCarRepository myCarRepository;
	private final CarOptionRepository carOptionRepository;

	private final Validator validator;

	public SummaryResponseDTO summary(Long userId, Long archivingId) {
		validator.verifyCarArchiving(userId, archivingId);

		final List<OptionSummaryDTO> optionSummaryDTOs = myCarRepository.findOptionSummaryByArchivingId(
			archivingId);

		Map<String, List<OptionSummaryDTO>> result = convertToOptionSummaryMap(
			optionSummaryDTOs);

		return new SummaryResponseDTO(result);
	}

	@Transactional
	public Long changeOptions(Long userId, EstimationChangeRequestDTO estimationChangeRequestDTO) {
		final Long archivingId = estimationChangeRequestDTO.getArchivingId();
		final List<Long> optionIds = estimationChangeRequestDTO.getOptionIds();

		validator.verifyCarArchiving(userId, archivingId);

		final List<CarOption> carOptions = carOptionRepository.findByIds(optionIds);
		final CarOption carOption = carOptions.get(0);
		validator.verifySameCategory(carOptions, CategoryDetail.valueOfName(carOption.getCategoryDetail()));

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
			String key = findCarOptionResultKey(optionSummaryDTO);
			if (!result.containsKey(key)) {
				result.put(key, new ArrayList<>());
			}

			if (Objects.equals(key, CHOICE.getName())) {
				List<String> childOptions = carOptionRepository.findStringByParentOptionId(
					optionSummaryDTO.getCarOptionId());
				optionSummaryDTO.setChildOptions(childOptions);
			}
			List<OptionSummaryDTO> values = result.get(key);
			values.add(optionSummaryDTO);
		}
		return result;
	}

	private String findCarOptionResultKey(OptionSummaryDTO optionSummaryDTO) {
		String category = optionSummaryDTO.getCategory();
		String categoryDetail = optionSummaryDTO.getCategoryDetail();

		if (Objects.equals(categoryDetail, MODEL.getName()))
			return MODEL.getName();
		else if (Objects.equals(categoryDetail, INTERIOR_COLOR.getName()))
			return INTERIOR_COLOR.getName();
		else if (Objects.equals(categoryDetail, EXTERIOR_COLOR.getName()))
			return EXTERIOR_COLOR.getName();
		else if (Objects.equals(category, CHOICE.getName())) {
			return CHOICE.getName();
		}
		return TRIM.getName();
	}
}
