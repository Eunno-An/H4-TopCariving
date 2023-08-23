package com.backend.topcariving.domain.service.archive;

import static com.backend.topcariving.domain.entity.archive.enums.ArchivingType.*;
import static com.backend.topcariving.domain.entity.option.enums.CategoryDetail.*;
import static com.backend.topcariving.domain.entity.option.enums.ExteriorColor.*;

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

import com.backend.topcariving.domain.dto.archive.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.dto.archive.response.CreatedCarDTO;
import com.backend.topcariving.domain.dto.archive.response.PictureSelectedOptionDTO;
import com.backend.topcariving.domain.dto.archive.response.PositionDTO;
import com.backend.topcariving.domain.dto.archive.response.SearchOptionDTO;
import com.backend.topcariving.domain.dto.archive.response.TotalReviewDTO;
import com.backend.topcariving.domain.dto.option.response.OptionDetailDTO;
import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.dto.projection.CarDTO;
import com.backend.topcariving.domain.entity.archive.Bookmark;
import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.entity.archive.MyCar;
import com.backend.topcariving.domain.entity.archive.enums.ArchivingType;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.Position;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.entity.review.CarReview;
import com.backend.topcariving.domain.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.repository.archive.BookmarkRepository;
import com.backend.topcariving.domain.repository.archive.CarArchivingRepository;
import com.backend.topcariving.domain.repository.archive.MyCarRepository;
import com.backend.topcariving.domain.repository.option.CarOptionRepository;
import com.backend.topcariving.domain.repository.option.PositionRepository;
import com.backend.topcariving.domain.repository.review.CarReviewRepository;
import com.backend.topcariving.domain.repository.review.TagReviewRepository;
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
		List<ArchivingType> archivingTypes = List.of(DRIVE, BUY);
		List<CarArchiving> carArchivings = optionIds == null ? getAllCarArchiving(archivingTypes, pageNumber, pageSize) : getFilterCarArchiving(optionIds, archivingTypes, pageNumber, pageSize);
		List<ArchiveFeedDTO> archiveSearchResponses = getArchiveSearchResponses(carArchivings);
		return ArchiveResponseDTO.of(searchOptions, archiveSearchResponses);
	}

	private List<SearchOptionDTO> getSearchOptions() {
		List<CarOption> carOptions = carOptionRepository.findByCategoryAndParentOptionIdIsNull(Category.CHOICE);
		return carOptions.stream()
			.map(SearchOptionDTO::from)
			.collect(Collectors.toList());
	}

	private List<CarArchiving> getAllCarArchiving(List<ArchivingType> archivingTypes, Integer pageNumber, Integer pageSize) {
		return carArchivingRepository.findByArchivingTypes(archivingTypes, pageNumber, pageSize);
	}

	private List<CarArchiving> getFilterCarArchiving(List<Long> carOptionIds, List<ArchivingType> archivingTypes, Integer pageNumber, Integer pageSize) {
		return carArchivingRepository.findByCarOptionIdsAndArchivingTypes(carOptionIds, archivingTypes, pageNumber, pageSize);
	}

	private List<ArchiveFeedDTO> getArchiveSearchResponses(List<CarArchiving> carArchivings) {
		return carArchivings.stream()
			.map(carArchiving -> ArchiveFeedDTO.of(carArchiving,
				createOptionMap(carArchiving),
				carReviewRepository.findByArchivingId(carArchiving.getArchivingId()).orElse(null),
				tagReviewRepository.findTagResponseDTOByArchivingId(carArchiving.getArchivingId())))
			.collect(Collectors.toList());
	}

	private Map<String, List<String>> createOptionMap(CarArchiving carArchiving) {
		List<CarOption> carOptions = carOptionRepository.findByArchivingId(carArchiving.getArchivingId());

		Map<String, List<String>> carArchiveResult = new HashMap<>();
		for (CarOption carOption : carOptions) {
			String key = Category.findCarOptionResultKey(carOption.getCategoryDetail(), carOption.getCategory());
			if (!carArchiveResult.containsKey(key)) {
				List<String> values = new ArrayList<>();
				carArchiveResult.put(key, values);
			}
			putValueInOptionMap(carArchiveResult, carOption, key);
		}
		return carArchiveResult;
	}

	private void putValueInOptionMap(Map<String, List<String>> carArchiveResult, CarOption carOption, String key) {
		List<String> values = carArchiveResult.get(key);
		values.add(carOption.getOptionName());
		carArchiveResult.put(key, values);
	}

	public ArchiveDetailResponseDTO getDetailsCars(Long userId, Long archivingId) {
		CarArchiving carArchiving = carArchivingRepository.findById(archivingId).orElseThrow(InvalidArchivingIdException::new);

		List<CarOption> carOptions = carOptionRepository.findByArchivingId(archivingId);
		int totalPrice = carOptions.stream().mapToInt(CarOption::getPrice).sum();

		CarOption colorOption = carOptionRepository.findByArchivingIdAndCategoryDetail(archivingId, EXTERIOR_COLOR)
			.orElseThrow(InvalidArchivingIdException::new);
		String photoUrl = getPhotoUrl(colorOption.getCarOptionId());

		List<PositionDTO> positions = createPositionDTO(carOptions);

		Optional<Bookmark> bookmark = bookmarkRepository.findByUserIdAndArchivingId(userId, archivingId);
		boolean isBookmarked = bookmark.isPresent() && bookmark.get().getIsAlive();

		List<OptionDetailDTO> optionDetailDTOs = createOptionDetailDTOs(carOptions, archivingId);

		CarReview carReview = carReviewRepository.findByArchivingId(archivingId).orElse(null);
		List<TagResponseDTO> tags = tagReviewRepository.findTagResponseDTOByArchivingId(archivingId);

		return ArchiveDetailResponseDTO.of(carArchiving, totalPrice, photoUrl, positions, isBookmarked, optionDetailDTOs, carReview, tags);
	}

	private List<PositionDTO> createPositionDTO(List<CarOption> carOptions) {
		List<Long> selectedOptionIds = carOptions.stream()
			.filter(carOption -> Objects.equals(carOption.getCategory(), Category.CHOICE))
			.map(CarOption::getCarOptionId)
			.collect(Collectors.toList());

		return positionRepository.findPositionDTOByCarOptionIds(selectedOptionIds);
	}

	private List<OptionDetailDTO> createOptionDetailDTOs(List<CarOption> carOptions, Long archivingId) {
		return carOptions.stream()
			.map(carOption -> createOptionDetailDTO(carOption, archivingId))
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
			.archivingType(DRIVE)
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
			if (carOption.getCategory() == Category.TRIM)
				result.put(carOption.getCategoryDetail().getName(), carOption.getOptionName());
		}

		return result;
	}

	private boolean isSelectedOptions(CategoryDetail categoryDetail) {
		return categoryDetail == SELECTED || categoryDetail == H_GENUINE_ACCESSORIES
			|| categoryDetail == N_PERFORMANCE;
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
			String key = Category.findCarOptionResultKey(carOption.getCategoryDetail(), carOption.getCategory());
			if (!result.containsKey(key)) {
				result.put(key, new ArrayList<>());
			}

			List<String> options = result.get(key);
			options.add(carOption.getOptionName());
		}

		return result;
	}

	@Transactional
	public Long deleteMyArchiving(Long userId, Long archivingId) {
		return toggleMyArchiving(false, userId, archivingId);
	}

	@Transactional
	public Long restoreMyArchiving(Long userId, Long archivingId) {
		return toggleMyArchiving(true, userId, archivingId);
	}

	private Long toggleMyArchiving(Boolean isAlive, Long userId, Long archivingId) {
		validator.verifyCarArchiving(userId, archivingId);

		carArchivingRepository.updateIsAliveByArchivingId(isAlive, archivingId);
		return archivingId;
	}
}
