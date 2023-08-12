package com.backend.topcariving.domain.option.service;

import static com.backend.topcariving.domain.option.entity.CategoryDetail.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.SelectOptionsRequestDTO;
import com.backend.topcariving.domain.option.dto.response.selection.SelectionResponseDTO;
import com.backend.topcariving.domain.option.dto.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

@DataJdbcTest
public class OptionServiceIntegralTest {

	@Autowired
	private CarOptionRepository carOptionRepository;

	@Autowired
	private CarArchivingRepository carArchivingRepository;

	@Autowired
	private MyCarRepository myCarRepository;

	private SoftAssertions softAssertions;

	private OptionService optionService;

	@BeforeEach
	void setup() {
		softAssertions = new SoftAssertions();

		optionService = new OptionService(carOptionRepository, carArchivingRepository, myCarRepository);
	}

	@AfterEach
	void after() {
		softAssertions.assertAll();
	}

	@Nested
	@DisplayName("기본 포함 품목 테스트")
	class basicOption {
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
	@DisplayName("선택 옵션 - 상세 품목 테스트")
	class selectedOption {
		@Test
		void 상세_품목_옵션을_반환해야한다() {
			// given, when
			List<OptionResponseDTO> selectedOptions = optionService.getSelections();

			// then
			softAssertions.assertThat(selectedOptions).hasSize(6);
			OptionResponseDTO selectedOption = selectedOptions.get(0);
			softAssertions.assertThat(selectedOption.getCarOptionId()).isEqualTo(103L);
			softAssertions.assertThat(selectedOption.getOptionName()).isEqualTo("컴포트 II");
			softAssertions.assertThat(selectedOption.getPrice()).isEqualTo(1090000);
			softAssertions.assertThat(selectedOption.getPhotoUrl()).isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg");
		}

		@Test
		void 상세_품목_옵션의_자식_옵션을_반환해야한다() {
			// given, when
			SelectionResponseDTO selectionResponseDTO = optionService.getSelectionDetails(103L);

			// then
			softAssertions.assertThat(selectionResponseDTO.getCarOptionId()).isEqualTo(103L);
			softAssertions.assertThat(selectionResponseDTO.getOptionName()).isEqualTo("컴포트 II");
			softAssertions.assertThat(selectionResponseDTO.getPrice()).isEqualTo(1090000);
			softAssertions.assertThat(selectionResponseDTO.getPhotoUrl()).isEqualTo("https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg");
			softAssertions.assertThat(selectionResponseDTO.getDetails()).hasSize(6);
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
			Long savedArchivingId = optionService.saveSelectionOptions(selectOptionsRequestDTO);

			// then
			softAssertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> myCarOption0 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionIds.get(0));
			Optional<MyCar> myCarOption1 = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionIds.get(1));
			softAssertions.assertThat(myCarOption0).isPresent();
			softAssertions.assertThat(myCarOption1).isPresent();
		}
	}
}
