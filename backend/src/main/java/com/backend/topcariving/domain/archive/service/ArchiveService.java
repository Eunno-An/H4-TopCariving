package com.backend.topcariving.domain.archive.service;

import static com.backend.topcariving.domain.archive.entity.ArchivingType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.dto.request.FeedCopyRequestDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.SearchOptionDTO;
import com.backend.topcariving.domain.archive.entity.CarArchiving;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.review.service.CarReviewService;
import com.backend.topcariving.domain.review.service.TagReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchiveService {

	private final CarOptionRepository carOptionRepository;
	private final MyCarRepository myCarRepository;
	private final CarArchivingRepository carArchivingRepository;

	private final CarReviewService carReviewService;
	private final TagReviewService tagReviewService;

	private static final int CAR = 0;

	public ArchiveResponseDTO archivingSearch(List<Long> optionIds) {
		List<SearchOptionDTO> searchOptions = getSearchOptions();
		List<String> archivingTypes = List.of(DRIVE.getType(), BUY.getType());
		List<CarArchiving> carArchivings = optionIds == null ? getAllCarArchiving(archivingTypes) : getFilterCarArchiving(optionIds, archivingTypes);
		List<ArchiveFeedDTO> archiveSearchResponses = getArchiveSearchResponses(carArchivings);
		return ArchiveResponseDTO.of(searchOptions, archiveSearchResponses);
	}

	private List<SearchOptionDTO> getSearchOptions() {
		List<CarOption> carOptions = carOptionRepository.findByCategoryAndParentOptionIdIsNull(CategoryDetail.CHOICE.getName());
		return carOptions.stream()
			.map(SearchOptionDTO::from)
			.collect(Collectors.toList());
	}

	private List<CarArchiving> getAllCarArchiving(List<String> archivingTypes) {
		return carArchivingRepository.findByArchivingTypes(archivingTypes);
	}

	private List<CarArchiving> getFilterCarArchiving(List<Long> optionIds, List<String> archivingTypes) {
		List<Long> archivingIds = myCarRepository.findArchivingIdByCarOptionId(optionIds);
		return carArchivingRepository.findByArchivingIdsAndArchivingTypes(archivingIds, archivingTypes);
	}

	private List<ArchiveFeedDTO> getArchiveSearchResponses(List<CarArchiving> carArchivings) {
		return carArchivings.stream()
			.map(carArchiving -> {
				MyCar myCar = myCarRepository.findByArchivingIdAndCarOptionIdIsNull(carArchiving.getArchivingId())
					.orElseThrow(InvalidArchivingIdException::new);
				return ArchiveFeedDTO.of(carArchiving,
					createOptionMap(carArchiving),
					carReviewService.getCarReview(myCar),
					tagReviewService.getTagResponseDTOs(myCar));
			})
			.collect(Collectors.toList());
	}

	private Map<String, List<String>> createOptionMap(CarArchiving carArchiving) {
		List<MyCar> myCars = myCarRepository.findByArchivingId(carArchiving.getArchivingId());
		myCars.removeIf(myCar -> Objects.equals(myCar.getCarOptionId(), CAR));

		List<Long> optionIds = myCars.stream()
			.map(MyCar::getCarOptionId)
			.collect(Collectors.toList());
		List<CarOption> carOptions = carOptionRepository.findByIds(optionIds);

		Map<String, List<String>> carArchiveResult = new HashMap<>();
		for (CarOption carOption : carOptions) {
			if (!carArchiveResult.containsKey(carOption.getCategoryDetail())) {
				List<String> values = new ArrayList<>();
				carArchiveResult.put(carOption.getCategoryDetail(), values);
			}
			putValueInOptionMap(carArchiveResult, carOption);
		}
		return carArchiveResult;
	}

	private void putValueInOptionMap(Map<String, List<String>> carArchiveResult, CarOption carOption) {
		List<String> values = carArchiveResult.get(carOption.getCategoryDetail());
		values.add(carOption.getOptionName());
		carArchiveResult.put(carOption.getCategoryDetail(), values);
	}

	@Transactional
	public Long saveFeedToCreatedCar(FeedCopyRequestDTO feedCopyRequestDTO) {

		CarArchiving newCarArchiving = CarArchiving.builder()
			.dayTime(LocalDateTime.now())
			.isComplete(true)
			.isAlive(true)
			.userId(feedCopyRequestDTO.getUserId())
			.build();

		newCarArchiving = carArchivingRepository.save(newCarArchiving);

		List<MyCar> myCars = myCarRepository.findByArchivingId(feedCopyRequestDTO.getArchivingId());
		List<MyCar> newCars = myCars.stream()
				.map(myCar -> new MyCar(null, myCar.getCarOptionId(), myCar.getArchivingId())).collect(Collectors.toList());

		myCarRepository.saveMultipleData(newCars);

		return newCarArchiving.getArchivingId();
	}
}
