package com.backend.topcariving.domain.option.service;

import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.entity.CarArchiving;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.option.dto.request.SelectOptionsRequestDTO;
import com.backend.topcariving.domain.option.dto.response.selection.SelectionResponseDTO;
import com.backend.topcariving.domain.option.dto.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.option.entity.CategoryDetail;

@SpringBootTest
@Transactional
public class OptionServiceIntegralTest extends TestSupport {

	@Autowired
	private MyCarRepository myCarRepository;

	@Autowired
	private CarArchivingRepository carArchivingRepository;

	@Autowired
	private OptionService optionService;

	@Nested
	@DisplayName("기본 포함 품목 테스트")
	class BasicOption {
		@Test
		void 기본_포함_품목_옵션을_반환해야한다() {
			// given, when
			Map<String, List<OptionResponseDTO>> basicOptions = optionService.getBasics().getData();

			// then
			softAssertions.assertThat(basicOptions.get(POWER_TRAIN.getName())).as("5가 반환되어야 함").hasSize(5);
			softAssertions.assertThat(basicOptions.get(SMART_SAFE_TECHNOLOGY.getName())).as("13이 반환되어야 함").hasSize(13);
			softAssertions.assertThat(basicOptions.get(SAFETY.getName())).as("4가 반환되어야 함").hasSize(4);
			softAssertions.assertThat(basicOptions.get(EXTERIOR.getName())).as("18이 반환되어야 함").hasSize(18);
			softAssertions.assertThat(basicOptions.get(INTERIOR.getName())).as("6이 반환되어야 함").hasSize(6);
			softAssertions.assertThat(basicOptions.get(SEAT.getName())).as("7이 반환되어야 함").hasSize(7);
			softAssertions.assertThat(basicOptions.get(CONVENIENCE.getName())).as("23이 반환되어야 함").hasSize(23);
			softAssertions.assertThat(basicOptions.get(MULTI_MEDIA.getName())).as("6이 반환되어야 함").hasSize(6);

		}
	}

	@Nested
	@DisplayName("상세 품목 테스트")
	class SelectedOption {
		@Test
		void 상세_품목_옵션을_반환해야한다() {
			// given, when
			List<OptionResponseDTO> selectedOptions = optionService.getSelections(SELECTED);

			// then
			softAssertions.assertThat(selectedOptions).as("6이 반환되어야 함").hasSize(6);
			OptionResponseDTO selectedOption = selectedOptions.get(0);
			softAssertions.assertThat(selectedOption.getCarOptionId()).as("103이 반환되어야 함").isEqualTo(103L);
			softAssertions.assertThat(selectedOption.getOptionName()).as("'컴포트 II'가 반환되어야 함").isEqualTo("컴포트 II");
			softAssertions.assertThat(selectedOption.getPrice()).as("1090000이 반환되어야 함").isEqualTo(1090000);
			softAssertions.assertThat(selectedOption.getPhotoUrl())
				.as("사진 경로가 반환되어야 함")
				.isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg");
		}

		@Test
		void 상세_품목_옵션을_저장하고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			List<Long> carOptionIds = List.of(103L, 110L);
			Long archivingId = 3L;
			final SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(userId, carOptionIds,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(archivingId)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO, SELECTED);

			// then
			softAssertions.assertThat(savedArchivingId).as("3이 반환되어야 함").isEqualTo(archivingId);
			Optional<MyCar> myCarOption0 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId,
				carOptionIds.get(0));
			Optional<MyCar> myCarOption1 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId,
				carOptionIds.get(1));
			softAssertions.assertThat(myCarOption0).as("0번째 요소 존재 여부 검증").isPresent();
			softAssertions.assertThat(myCarOption1).as("1번째 요소 존재 여부 검증").isPresent();
		}

		@Test
		void 상세_품목_변경_시_이미_값이_있을_경우_값이_업데이트_되어야_한다() {
			// given
			Long userId = 1L;
			List<Long> ids = List.of(113L, 114L, 115L);
			Long archivingId = 1L;
			final SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(userId, ids,
				archivingId);

			// when
			final Long returnedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO, SELECTED);

			// then
			final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 103L) || Objects.equals(myCar.getCarOptionId(), 110L))
				.findFirst()).isEmpty();
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 113L))
				.findFirst()).isPresent();
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 114L))
				.findFirst()).isPresent();
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 115L))
				.findFirst()).isPresent();
		}
	}

	@Nested
	@DisplayName("H Genuine Accessories 테스트")
	class HGenuineAccessories {
		@Test
		void H_Genuine_Accessories_옵션을_반환해야한다() {
			// given, when
			List<OptionResponseDTO> selectedOptions = optionService.getSelections(H_GENUINE_ACCESSORIES);

			// then
			softAssertions.assertThat(selectedOptions).hasSize(6);
			OptionResponseDTO selectedOption = selectedOptions.get(0);
			softAssertions.assertThat(selectedOption.getCarOptionId()).isEqualTo(120L);
			softAssertions.assertThat(selectedOption.getOptionName()).isEqualTo("듀얼 머플러 패키지");
			softAssertions.assertThat(selectedOption.getPrice()).isEqualTo(840000);
			softAssertions.assertThat(selectedOption.getPhotoUrl())
				.isEqualTo(
					"https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/dualmufflerpackage.jpeg");
		}

		@Test
		void H_Genuine_Accessories_옵션을_저장하고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			List<Long> carOptionIds = List.of(121L, 122L, 123L, 124L, 125L);
			Long archivingId = 3L;
			final SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(userId, carOptionIds,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(archivingId)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO, H_GENUINE_ACCESSORIES);

			// then
			softAssertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> myCarOption0 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId,
				carOptionIds.get(0));
			Optional<MyCar> myCarOption1 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId,
				carOptionIds.get(1));
			softAssertions.assertThat(myCarOption0).isPresent();
			softAssertions.assertThat(myCarOption1).isPresent();
		}

		@Test
		void H_Genuine_Accessories_변경_시_이미_값이_있을_경우_값이_업데이트_되어야_한다() {
			// given
			Long userId = 1L;
			List<Long> ids = List.of(123L, 125L);
			Long archivingId = 1L;
			final SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(userId, ids,
				archivingId);

			final SelectOptionsRequestDTO selectedSavedData = new SelectOptionsRequestDTO(userId, List.of(121L),
				archivingId);
			optionService.saveSelectionOptions(selectedSavedData, H_GENUINE_ACCESSORIES);

			// when
			final Long returnedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO,
				H_GENUINE_ACCESSORIES);

			// then
			final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 123L))
				.findFirst()).isPresent();
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 125L))
				.findFirst()).isPresent();
		}
	}

	@Nested
	@DisplayName("N Performance 테스트")
	class NPerformance {
		@Test
		void N_Performance_옵션을_반환해야한다() {
			// given, when
			List<OptionResponseDTO> selectedOptions = optionService.getSelections(N_PERFORMANCE);

			// then
			softAssertions.assertThat(selectedOptions).hasSize(3);
			OptionResponseDTO selectedOption = selectedOptions.get(0);
			softAssertions.assertThat(selectedOption.getCarOptionId()).isEqualTo(128L);
			softAssertions.assertThat(selectedOption.getOptionName()).isEqualTo("20인치 다크 스퍼터링 휠");
			softAssertions.assertThat(selectedOption.getPrice()).isEqualTo(840000);
			softAssertions.assertThat(selectedOption.getPhotoUrl())
				.isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20_darkwheel.jpeg");
		}

		@Test
		void N_Performance_옵션을_저장하고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			Long carOptionId = 130L;
			Long archivingId = 3L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(userId, carOptionId,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(archivingId)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = optionService.saveSelectionOption(selectOptionRequestDTO, N_PERFORMANCE);

			// then
			softAssertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> myCarOption = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId);
			softAssertions.assertThat(myCarOption).isPresent();
		}

		@Test
		void N_Performance_변경_시_이미_값이_있을_경우_값이_업데이트_되고_아카이빙의_is_complete_값이_true가_되어야_한다() {
			// given
			Long userId = 1L;
			Long carOptionId = 128L;
			Long archivingId = 1L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(userId, carOptionId,
				archivingId);

			final SelectOptionRequestDTO selectedSavedData = new SelectOptionRequestDTO(userId, 130L, archivingId);
			optionService.saveSelectionOption(selectedSavedData, N_PERFORMANCE);

			// when
			final Long returnedArchivingId = optionService.saveSelectionOption(selectOptionRequestDTO, N_PERFORMANCE);

			// then
			final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);

			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 130L))
				.findAny()).isEmpty();
			softAssertions.assertThat(myCars.stream()
				.filter(myCar -> Objects.equals(myCar.getCarOptionId(), 128L))
				.findFirst()).isPresent();

			Optional<CarArchiving> findCarArchiving = carArchivingRepository.findById(archivingId);
			final CarArchiving carArchiving = findCarArchiving.get();
			softAssertions.assertThat(carArchiving.getIsComplete()).isTrue();
		}

		@Test
		void N_Performance에서_아무것도_입력하지_않는다면_저장하지_않고_그대로_아카이빙_아이디를_반환한다() {
			// given
			SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(1L, null, 1L);

			// when
			Long archivingId = optionService.saveSelectionOption(selectOptionRequestDTO, N_PERFORMANCE);

			// then
			List<MyCar> findCar = myCarRepository.findByCategoryDetailAndArchivingId(N_PERFORMANCE.getName(),
				archivingId);
			Assertions.assertThat(findCar).isEmpty();
		}
	}

	@Nested
	@DisplayName("선택 옵션 상세 조회 테스트")
	class SelectionOptionDetail {
		@Test
		void 선택_옵션의_자식_옵션을_반환해야한다() {
			// given, when
			SelectionResponseDTO selectionResponseDTO = optionService.getSelectionDetails(103L);

			// then
			softAssertions.assertThat(selectionResponseDTO.getCarOptionId()).isEqualTo(103L);
			softAssertions.assertThat(selectionResponseDTO.getOptionName()).isEqualTo("컴포트 II");
			softAssertions.assertThat(selectionResponseDTO.getPrice()).isEqualTo(1090000);
			softAssertions.assertThat(selectionResponseDTO.getPhotoUrl())
				.isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg");
			softAssertions.assertThat(selectionResponseDTO.getDetails()).hasSize(6);
			softAssertions.assertThat(selectionResponseDTO.getTags()).hasSize(5);
		}
	}

	@ParameterizedTest
	@MethodSource("generateCategoryAndSelectOptions")
	void 선택_옵션에서_아무것도_입력하지_않는다면_저장하지_않고_그대로_아카이빙_아이디를_반환한다(CategoryDetail categoryDetail,
		SelectOptionsRequestDTO selectOptionsRequestDTO) {
		//given, when
		Long archivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO, categoryDetail);

		// then
		List<MyCar> findCar = myCarRepository.findByCategoryDetailAndArchivingId(categoryDetail.getName(),
			archivingId);
		Assertions.assertThat(findCar).isEmpty();
	}

	static Stream<Arguments> generateCategoryAndSelectOptions() {
		return Stream.of(
			Arguments.of(SELECTED, new SelectOptionsRequestDTO(1L, new ArrayList<>(), 1L)),
			Arguments.of(H_GENUINE_ACCESSORIES, new SelectOptionsRequestDTO(1L, new ArrayList<>(), 1L))
		);
	}
}
