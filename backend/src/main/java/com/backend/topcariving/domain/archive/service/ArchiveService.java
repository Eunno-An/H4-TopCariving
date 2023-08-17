package com.backend.topcariving.domain.archive.service;

import static com.backend.topcariving.domain.archive.entity.ArchivingType.*;
import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.dto.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.OptionDetailDTO;
import com.backend.topcariving.domain.archive.dto.response.PositionDTO;
import com.backend.topcariving.domain.archive.dto.response.SearchOptionDTO;
import com.backend.topcariving.domain.archive.entity.CarArchiving;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.bookmark.entity.Bookmark;
import com.backend.topcariving.domain.bookmark.repository.BookmarkRepository;
import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.entity.Position;
import com.backend.topcariving.domain.option.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.option.repository.PositionRepository;
import com.backend.topcariving.domain.review.entity.CarReview;
import com.backend.topcariving.domain.review.repository.CarReviewRepository;
import com.backend.topcariving.domain.review.repository.TagReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchiveService {

	private final CarOptionRepository carOptionRepository;
	private final MyCarRepository myCarRepository;
	private final CarArchivingRepository carArchivingRepository;
	private final CarReviewRepository carReviewRepository;
	private final TagReviewRepository tagReviewRepository;
	private final PositionRepository positionRepository;
	private final BookmarkRepository bookmarkRepository;

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

	private List<CarArchiving> getFilterCarArchiving(List<Long> carOptionIds, List<String> archivingTypes) {
		return carArchivingRepository.findByCarOptionIdsAndArchivingTypes(carOptionIds, archivingTypes);
	}

	private List<ArchiveFeedDTO> getArchiveSearchResponses(List<CarArchiving> carArchivings) {
		return carArchivings.stream()
			.map(carArchiving -> {
				return ArchiveFeedDTO.of(carArchiving,
					createOptionMap(carArchiving),
					carReviewRepository.findByArchivingId(carArchiving.getArchivingId()).orElse(null),
					tagReviewRepository.findTagResponseDTOByArchivingId(carArchiving.getArchivingId()));
			})
			.collect(Collectors.toList());
	}

	private Map<String, List<String>> createOptionMap(CarArchiving carArchiving) {
		List<CarOption> carOptions = carOptionRepository.findByArchivingId(carArchiving.getArchivingId());

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

	public ArchiveDetailResponseDTO getDetailsCars(Long userId, Long archivingId) {
		CarArchiving carArchiving = carArchivingRepository.findById(archivingId).orElseThrow(InvalidArchivingIdException::new);
		List<CarOption> carOptions = carOptionRepository.findByArchivingId(archivingId);
		int totalPrice = carOptions.stream().mapToInt(CarOption::getPrice).sum();
		List<PositionDTO> positions = createPositionDTO(carOptions);
		Optional<Bookmark> bookmark = bookmarkRepository.findByUserIdAndArchivingId(userId, archivingId);
		boolean isBookmarked = bookmark.isPresent() && bookmark.get().getIsAlive();
		List<OptionDetailDTO> optionDetailDTOs = createOptionDetailDTOs(carOptions, archivingId);
		CarReview carReview = carReviewRepository.findByArchivingId(archivingId).orElse(null);
		List<TagResponseDTO> tags = tagReviewRepository.findTagResponseDTOByArchivingId(archivingId);

		return ArchiveDetailResponseDTO.of(carArchiving, totalPrice, positions, isBookmarked, optionDetailDTOs, carReview, tags);
	}

	private List<PositionDTO> createPositionDTO(List<CarOption> carOptions) {
		List<Long> selectedOptionIds = carOptions.stream()
			.filter(carOption -> carOption.getCategory().equals(CHOICE.getName()))
			.map(CarOption::getCarOptionId)
			.collect(Collectors.toList());

		List<Position> positions = positionRepository.findByCarOptionIds(selectedOptionIds);

		return positions.stream()
			.map(PositionDTO::from)
			.collect(Collectors.toList());
	}

	private List<OptionDetailDTO> createOptionDetailDTOs(List<CarOption> carOptions, Long archivingId) {
		return carOptions.stream()
			.map(carOption -> {
				return createOptionDetailDTO(carOption, archivingId);
			})
			.collect(Collectors.toList());
	}

	private OptionDetailDTO createOptionDetailDTO(CarOption carOption, Long archivingId) {
		List<CarOption> childCarOptions = carOptionRepository.findByParentOptionId(carOption.getCarOptionId());
		List<String> childOptionNames = childCarOptions.stream()
			.map(CarOption::getOptionName)
			.collect(Collectors.toList());
		Position position = positionRepository.findByCarOptionId(carOption.getCarOptionId()).orElse(null);
		Long positionId = position == null ? null : position.getPositionId();
		List<TagResponseDTO> tags = tagReviewRepository.findTagResponseDTOByArchivingIdAndCarOptionId(archivingId, carOption.getCarOptionId());
		return OptionDetailDTO.of(carOption, childOptionNames, positionId, tags);
	}

	@Transactional
	public Long saveFeedToCreatedCar(Long userId, Long archivingId) {

		CarArchiving newCarArchiving = CarArchiving.builder()
			.dayTime(LocalDateTime.now())
			.isComplete(true)
			.isAlive(true)
			.userId(userId)
			.build();

		newCarArchiving = carArchivingRepository.save(newCarArchiving);

		List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);
		List<MyCar> newCars = myCars.stream()
				.map(myCar -> new MyCar(null, myCar.getCarOptionId(), myCar.getArchivingId())).collect(Collectors.toList());

		myCarRepository.saveMultipleData(newCars);

		return newCarArchiving.getArchivingId();
	}
}
