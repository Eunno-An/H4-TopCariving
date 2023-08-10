package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.option.dto.response.engine.EngineResponseDTO;
import com.backend.topcariving.domain.option.dto.response.model.ModelResponseDTO;
import com.backend.topcariving.domain.option.dto.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.option.repository.EngineDetailRepository;
import com.backend.topcariving.domain.option.repository.ModelPhotoRepository;

@DataJdbcTest
public class TrimServiceIntegralTest {

	@Autowired
	private CarOptionRepository carOptionRepository;

	@Autowired
	private ModelPhotoRepository modelPhotoRepository;

	@Autowired
	private CarArchivingRepository carArchivingRepository;

	@Autowired
	private MyCarRepository myCarRepository;

	@Autowired
	private EngineDetailRepository engineDetailRepository;

	private SoftAssertions softAssertions;

	@BeforeEach
	void setup() {
		softAssertions = new SoftAssertions();

		trimService = new TrimService(carOptionRepository,
			modelPhotoRepository,
			carArchivingRepository,
			myCarRepository,
			engineDetailRepository);
	}

	private TrimService trimService;

	@Nested
	@DisplayName("Model 테스트")
	class model {
		@Test
		void 모델의_종류를_반환해야한다() {
			// given, when
			List<ModelResponseDTO> models = trimService.getModels();

			// then
				ModelResponseDTO model = models.get(0);
			softAssertions.assertThat(models).as("사이즈 테스트").hasSize(4);
			softAssertions.assertThat(model.getCarOptionId()).as("옵션 아이디 테스트").isEqualTo(1L);
			softAssertions.assertThat(model.getOptionName()).as("옵션 이름 테스트").isEqualTo("Le Blanc");
			softAssertions.assertThat(model.getPrice()).as("옵션 가격 테스트").isEqualTo(41980000);
			softAssertions.assertAll();
		}

		@Test
		void 모델이_제대로_저장되고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 1L;
			Long carOptionId = 1L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(userId, carOptionId, null);

			// when
			Long archivingId = trimService.saveModel(selectOptionRequestDTO);

			// then
			Assertions.assertThat(archivingId).isEqualTo(21);
		}
	}

	@Nested
	@DisplayName("Engine 테스트")
	class Engine {
		@Test
		void 엔진의_종류를_반환해야한다() {
			// given, when
			List<EngineResponseDTO> engines = trimService.getEngines();

			// then
			softAssertions.assertThat(engines).hasSize(2);
			EngineResponseDTO engineResponseDTO = engines.get(0);
			softAssertions.assertThat(engineResponseDTO.getOptionName()).isEqualTo("디젤 2.2");
			softAssertions.assertAll();
		}

		@Test
		void 엔진이_제대로_저장되고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			Long carOptionId = 5L;
			Long archivingId = 3L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(userId, carOptionId,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(3L)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = trimService.saveTrim(selectOptionRequestDTO, CategoryDetail.ENGINE);

			// then
			Assertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> findMyCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId);
			Assertions.assertThat(findMyCar).isPresent();
		}
	}

	@Nested
	@DisplayName("바디타입 테스트")
	class bodyType {
		@Test
		void 바디타입의_종류를_반환해야_한다() {
			// given, when
			final List<OptionResponseDTO> bodyTypes = trimService.getOptions(CategoryDetail.BODY_TYPE);

			// then
			softAssertions.assertThat(bodyTypes).as("사이즈 테스트").hasSize(2);
			final OptionResponseDTO optionResponseDTO = bodyTypes.get(0);
			softAssertions.assertThat(optionResponseDTO.getCarOptionId()).as("옵션 아이디 테스트").isEqualTo(7L);
			softAssertions.assertThat(optionResponseDTO.getOptionName()).as("옵션 이름 테스트").isEqualTo("7인승");
			softAssertions.assertAll();
		}

		@Test
		void 바디타입이_제대로_저장되고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			Long carOptionId = 7L;
			Long archivingId = 3L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(userId, carOptionId,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(3L)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = trimService.saveTrim(selectOptionRequestDTO, CategoryDetail.BODY_TYPE);

			// then
			Assertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> findMyCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId);
			Assertions.assertThat(findMyCar).isPresent();
		}
	}
}
