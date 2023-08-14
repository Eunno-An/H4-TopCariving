package com.backend.topcariving.domain.option.service;

import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.option.dto.request.SelectOptionsRequestDTO;
import com.backend.topcariving.domain.option.dto.response.selection.SelectionResponseDTO;
import com.backend.topcariving.domain.option.dto.response.trim.OptionResponseDTO;

@SpringBootTest
@Transactional
public class OptionServiceIntegralTest extends TestSupport {

	@Autowired
	private MyCarRepository myCarRepository;

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
			softAssertions.assertThat(basicOptions.get(POWER_TRAIN.getName())).hasSize(5);
			softAssertions.assertThat(basicOptions.get(SMART_SAFE_TECHNOLOGY.getName())).hasSize(13);
			softAssertions.assertThat(basicOptions.get(SAFETY.getName())).hasSize(4);
			softAssertions.assertThat(basicOptions.get(EXTERIOR.getName())).hasSize(18);
			softAssertions.assertThat(basicOptions.get(INTERIOR.getName())).hasSize(6);
			softAssertions.assertThat(basicOptions.get(SEAT.getName())).hasSize(7);
			softAssertions.assertThat(basicOptions.get(CONVENIENCE.getName())).hasSize(23);
			softAssertions.assertThat(basicOptions.get(MULTI_MEDIA.getName())).hasSize(6);

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
			softAssertions.assertThat(selectedOptions).hasSize(6);
			OptionResponseDTO selectedOption = selectedOptions.get(0);
			softAssertions.assertThat(selectedOption.getCarOptionId()).isEqualTo(103L);
			softAssertions.assertThat(selectedOption.getOptionName()).isEqualTo("컴포트 II");
			softAssertions.assertThat(selectedOption.getPrice()).isEqualTo(1090000);
			softAssertions.assertThat(selectedOption.getPhotoUrl()).isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg");
		}

		@Test
		void 상세_품목_옵션을_저장하고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			List<Long> carOptionIds = List.of(103L, 110L);
			Long archivingId = 3L;
			final SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(userId, carOptionIds, archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(archivingId)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO, SELECTED);

			// then
			softAssertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> myCarOption0 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionIds.get(0));
			Optional<MyCar> myCarOption1 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionIds.get(1));
			softAssertions.assertThat(myCarOption0).isPresent();
			softAssertions.assertThat(myCarOption1).isPresent();
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
			softAssertions.assertThat(selectedOption.getPhotoUrl()).isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/H_Genuine_Accessories/dualmufflerpackage.jpeg");
		}

		@Test
		void H_Genuine_Accessories_옵션을_저장하고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			List<Long> carOptionIds = List.of(121L, 122L, 123L, 124L, 125L);
			Long archivingId = 3L;
			final SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(userId, carOptionIds, archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(archivingId)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO, H_GENUINE_ACCESSORIES);

			// then
			softAssertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> myCarOption0 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionIds.get(0));
			Optional<MyCar> myCarOption1 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionIds.get(1));
			softAssertions.assertThat(myCarOption0).isPresent();
			softAssertions.assertThat(myCarOption1).isPresent();
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
			softAssertions.assertThat(selectedOption.getPhotoUrl()).isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20_darkwheel.jpeg");
		}

		@Test
		void N_Performance_옵션을_저장하고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			Long carOptionId = 130L;
			Long archivingId = 3L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(userId, carOptionId, archivingId);
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
			softAssertions.assertThat(selectionResponseDTO.getPhotoUrl()).isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg");
			softAssertions.assertThat(selectionResponseDTO.getDetails()).hasSize(6);
			softAssertions.assertThat(selectionResponseDTO.getTags()).hasSize(5);
		}
	}
}
