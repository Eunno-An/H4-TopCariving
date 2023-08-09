package com.backend.topcariving.domain.option.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.engine.EngineResponseDTO;
import com.backend.topcariving.domain.option.dto.model.ModelResponseDTO;
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

	@Test
	void 모델의_종류를_반환해야한다() {
		// given, when
		List<ModelResponseDTO> models = trimService.getModels();

		// then
		softAssertions.assertThat(models).as("사이즈 테스트").hasSize(4);
		ModelResponseDTO model = models.get(0);
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

		// when
		Long archivingId = trimService.saveModel(userId, carOptionId);

		// then
		Assertions.assertThat(archivingId).isEqualTo(21);
	}

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
		MyCar myCar = MyCar.builder()
			.archivingId(3L)
			.carOptionId(1L)
			.build();
		myCarRepository.save(myCar);

		// when
		Long savedArchivingId = trimService.saveEngine(userId, carOptionId, archivingId);

		// then
		Assertions.assertThat(savedArchivingId).isEqualTo(archivingId);
		Optional<MyCar> findMyCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId);
		Assertions.assertThat(findMyCar.isPresent()).isTrue();
	}
}
