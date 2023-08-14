package com.backend.topcariving.domain.option.service;

import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingColorResponseDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingOptionDetailResponseDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingOptionResponseDTO;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingResponseDTO;
import com.backend.topcariving.domain.option.dto.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.option.dto.response.estimation.SummaryResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.exception.InvalidCategoryException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimationService {

	private final CarArchivingRepository carArchivingRepository;
	private final MyCarRepository myCarRepository;
	private final CarOptionRepository carOptionRepository;

	public ArchivingResponseDTO getArchivingResult(Long userId, Long archivingId) {
		verifyCarArchiving(userId, archivingId);

		List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);

		Map<CategoryDetail, ArchivingOptionResponseDTO> archivingOptionResponseDTOs = new HashMap<>();
		Map<CategoryDetail, ArchivingColorResponseDTO> archivingColorResponseDTOs = new HashMap<>();
		List<ArchivingOptionDetailResponseDTO> selectOptionResponseDTOs = new ArrayList<>();

		for (MyCar myCar : myCars) {
			CarOption carOption = carOptionRepository.findByCarOptionId(myCar.getCarOptionId())
				.orElseThrow(InvalidCarOptionIdException::new);

			switch (valueOfName(carOption.getCategoryDetail())) {
				case MODEL:
					archivingOptionResponseDTOs.put(MODEL, ArchivingOptionResponseDTO.from(carOption));
					break;
				case ENGINE:
					archivingOptionResponseDTOs.put(ENGINE, ArchivingOptionResponseDTO.from(carOption));
					break;
				case BODY_TYPE:
					archivingOptionResponseDTOs.put(BODY_TYPE, ArchivingOptionResponseDTO.from(carOption));
					break;
				case DRIVING_METHOD:
					archivingOptionResponseDTOs.put(DRIVING_METHOD, ArchivingOptionResponseDTO.from(carOption));
					break;
				case EXTERIOR_COLOR:
					archivingColorResponseDTOs.put(EXTERIOR_COLOR, ArchivingColorResponseDTO.from(carOption));
					break;
				case INTERIOR_COLOR:
					archivingColorResponseDTOs.put(INTERIOR_COLOR, ArchivingColorResponseDTO.from(carOption));
					break;
				case SELECTED:
				case H_GENUINE_ACCESSORIES:
				case N_PERFORMANCE:
					List<CarOption> childCarOptions = carOptionRepository.findByParentOptionId(
						carOption.getCarOptionId());
					selectOptionResponseDTOs.add(ArchivingOptionDetailResponseDTO.of(carOption,
						ArchivingOptionResponseDTO.from(childCarOptions)));
					break;
				default:
					throw new InvalidCarOptionIdException();
			}
		}

		return ArchivingResponseDTO.of(archivingId, archivingOptionResponseDTOs, archivingColorResponseDTOs,
			selectOptionResponseDTOs);
	}

	public SummaryResponseDTO summary(Long userId, Long archivingId) {
		verifyCarArchiving(userId, archivingId);

		final List<OptionSummaryDTO> OptionSummaryDTOs = myCarRepository.findOptionSummaryByArchivingId(
			archivingId);

		Map<String, List<OptionSummaryDTO>> result = convertToOptionSummaryMap(
			OptionSummaryDTOs);

		return new SummaryResponseDTO(result);
	}

	public Long changeOptions(EstimationChangeRequestDTO estimationChangeRequestDTO) {
		final Long archivingId = estimationChangeRequestDTO.getArchivingId();
		final Long userId = estimationChangeRequestDTO.getUserId();
		final List<Long> optionIds = estimationChangeRequestDTO.getOptionIds();

		verifyCarArchiving(userId, archivingId);

		final List<CarOption> carOptions = carOptionRepository.findByIds(optionIds);
		verifyModifyOnlySameCategory(carOptions);

		final CarOption carOption = carOptions.get(0);
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
		final List<OptionSummaryDTO> OptionSummaryDTOs) {
		Map<String, List<OptionSummaryDTO>> result = new HashMap<>();

		for (OptionSummaryDTO optionSummaryDTO : OptionSummaryDTOs) {
			if (!result.containsKey(optionSummaryDTO.getCategory())) {
				List<OptionSummaryDTO> values = new ArrayList<>();
				result.put(optionSummaryDTO.getCategory(), values);
			}
			putValueInOptionSummary(result, optionSummaryDTO);
		}
		return result;
	}

	private void putValueInOptionSummary(final Map<String, List<OptionSummaryDTO>> result,
		final OptionSummaryDTO optionSummaryDTO) {
		if (optionSummaryDTO.getCategoryDetail().equals(MODEL.getName())) {
			List<OptionSummaryDTO> values = new ArrayList<>();
			values.add(optionSummaryDTO);
			result.put(MODEL.getName(), values);
			return;
		}

		final List<OptionSummaryDTO> values = result.get(optionSummaryDTO.getCategory());
		values.add(optionSummaryDTO);
		result.put(optionSummaryDTO.getCategory(), values);
	}

	private void verifyCarArchiving(Long userId, Long archivingId) {
		if (!carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId)) {
			throw new InvalidAuthorityException();
		}
	}

	private void verifyModifyOnlySameCategory(List<CarOption> carOptions) {
		final CarOption carOption = carOptions.get(0);
		final String category = carOption.getCategoryDetail();

		for (CarOption option : carOptions) {
			if (!option.getCategoryDetail().equals(category))
				throw new InvalidCategoryException();
		}
	}
}
