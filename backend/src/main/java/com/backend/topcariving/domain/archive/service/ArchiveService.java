package com.backend.topcariving.domain.archive.service;

import static com.backend.topcariving.domain.archive.entity.ArchivingType.*;
import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.archive.dto.CarDTO;
import com.backend.topcariving.domain.archive.dto.TotalReviewDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.CreatedCarDTO;
import com.backend.topcariving.domain.archive.dto.response.OptionDetailDTO;
import com.backend.topcariving.domain.archive.dto.response.PictureSelectedOptionDTO;
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
import com.backend.topcariving.global.utils.Validator;

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

	private final Validator validator;

	public ArchiveResponseDTO archivingSearch(List<Long> optionIds, Integer pageNumber, Integer pageSize) {
		List<SearchOptionDTO> searchOptions = getSearchOptions();
		List<String> archivingTypes = List.of(DRIVE.getType(), BUY.getType());
		List<CarArchiving> carArchivings = optionIds == null ? getAllCarArchiving(archivingTypes, pageNumber, pageSize) : getFilterCarArchiving(optionIds, archivingTypes, pageNumber, pageSize);
		List<ArchiveFeedDTO> archiveSearchResponses = getArchiveSearchResponses(carArchivings);
		return ArchiveResponseDTO.of(searchOptions, archiveSearchResponses);
	}

	private List<SearchOptionDTO> getSearchOptions() {
		List<CarOption> carOptions = carOptionRepository.findByCategoryAndParentOptionIdIsNull(CategoryDetail.CHOICE.getName());
		return carOptions.stream()
			.map(SearchOptionDTO::from)
			.collect(Collectors.toList());
	}

	private List<CarArchiving> getAllCarArchiving(List<String> archivingTypes, Integer pageNumber, Integer pageSize) {
		return carArchivingRepository.findByArchivingTypes(archivingTypes, pageNumber, pageSize);
	}

	private List<CarArchiving> getFilterCarArchiving(List<Long> carOptionIds, List<String> archivingTypes, Integer pageNumber, Integer pageSize) {
		return carArchivingRepository.findByCarOptionIdsAndArchivingTypes(carOptionIds, archivingTypes, pageNumber, pageSize);
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

		return positionRepository.findPositionDTOByCarOptionIds(selectedOptionIds);
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
		List<MyCar> newCars = new ArrayList<>();
		for (MyCar myCar : myCars) {
			newCars.add(new MyCar(null, myCar.getCarOptionId(), newCarArchiving.getArchivingId()));
		}		myCarRepository.saveMultipleData(newCars);

		return newCarArchiving.getArchivingId();
	}

	public List<CreatedCarDTO> getCreatedCars(Long userId, Integer pageNumber, Integer pageSize) {

		List<CarDTO> cars = carArchivingRepository.findCarDTOByUserIdAndOffsetAndPageSize(
			userId, pageNumber, pageSize);

		if (cars.isEmpty())
			return new ArrayList<>();

		List<CreatedCarDTO> result = new ArrayList<>();
		for (CarDTO car : cars) {
			CreatedCarDTO createdCar = CreatedCarDTO.builder()
				.archivingId(car.getArchivingId())
				.trims(getTrims(car.getCarOptions()))
				.selectedOptions(getPictures(car.getCarOptions()))
				.dayTime(car.getDayTime())
				.isComplete(car.getIsComplete())
				.build();
			result.add(createdCar);
		}

		return result;
	}

	private List<PictureSelectedOptionDTO> getPictures(List<CarOption> carOptions) {
		return carOptions.stream()
			.filter(carOption -> isSelectedOptions(carOption.getCategoryDetail()))
			.map(carOption -> new PictureSelectedOptionDTO(carOption.getOptionName(), carOption.getPhotoUrl()))
			.collect(Collectors.toList());
	}

	private Map<String, String> getTrims(List<CarOption> carOptions) {

		Map<String, String> result = new HashMap<>();

		for (CarOption carOption : carOptions) {
			if (Objects.equals(carOption.getCategory(), TRIM.getName()))
				result.put(carOption.getCategoryDetail(), carOption.getOptionName());
		}

		return result;
	}

	private List<Long> getArchivingIds(List<CarArchiving> carArchivings) {
		return carArchivings.stream()
			.map(CarArchiving::getArchivingId)
			.collect(Collectors.toList());
	}

	private boolean isSelectedOptions(String categoryDetail) {
		return Objects.equals(categoryDetail, SELECTED.getName()) || Objects.equals(categoryDetail, H_GENUINE_ACCESSORIES.getName())
			|| Objects.equals(categoryDetail, N_PERFORMANCE.getName());
	}

	public List<ArchiveFeedDTO> getFeedCars(Long userId, Integer pageNumber, Integer pageSize) {

		List<CarDTO> cars = carArchivingRepository.findCarDTOByUserIdAndOffsetAndPageSizeAndAliveTrue(
			userId, pageNumber, pageSize);
		if (cars.isEmpty())
			return new ArrayList<>();

		List<Long> archivingIds = cars.stream()
			.map(CarDTO::getArchivingId)
			.collect(Collectors.toList());
		Map<Long, TotalReviewDTO> totalReviews = carReviewRepository.findTotalReviewDTOsByArchivingIds(
			archivingIds);

		List<ArchiveFeedDTO> result = new ArrayList<>();
		for (CarDTO car : cars) {
			TotalReviewDTO totalReviewDTO = totalReviews.get(car.getArchivingId());
			ArchiveFeedDTO feedDTO = ArchiveFeedDTO.builder()
				.archivingId(car.getArchivingId())
				.carArchiveResult(getCarArchiveResult(car.getCarOptions()))
				.dayTime(car.getDayTime())
				.carReview(totalReviewDTO == null ? null : totalReviewDTO.getReview())
				.tags(totalReviewDTO == null ? null : totalReviewDTO.getTags())
				.type(car.getArchivingType())
				.build();
			result.add(feedDTO);
		}

		return result;
	}

	private Map<String, List<String>> getCarArchiveResult(List<CarOption> carOptions) {
		Map<String, List<String>> result = new HashMap<>();

		for (CarOption carOption : carOptions) {
			String key = findCarOptionResultKey(carOption);
			if (!result.containsKey(key)) {
				result.put(key, new ArrayList<>());
			}

			List<String> options = result.get(key);
			options.add(carOption.getOptionName());
		}

		return result;
	}

	private String findCarOptionResultKey(CarOption carOption) {
		String category = carOption.getCategory();
		String categoryDetail = carOption.getCategoryDetail();

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

	public Long deleteMyArchiving(Long userId, Long archivingId) {
		return toggleMyArchiving(false, userId, archivingId);
	}

	public Long restoreMyArchiving(Long userId, Long archivingId) {
		return toggleMyArchiving(true, userId, archivingId);
	}

	private Long toggleMyArchiving(Boolean isAlive, Long userId, Long archivingId) {
		validator.verifyCarArchiving(userId, archivingId);

		carArchivingRepository.updateIsAliveByArchivingId(isAlive, archivingId);
		return archivingId;
	}
}
