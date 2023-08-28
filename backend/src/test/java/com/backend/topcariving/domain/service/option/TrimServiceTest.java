package com.backend.topcariving.domain.service.option;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.entity.archive.MyCar;
import com.backend.topcariving.domain.repository.archive.CarArchivingRepository;
import com.backend.topcariving.domain.repository.archive.MyCarRepository;
import com.backend.topcariving.domain.dto.option.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.dto.option.response.engine.EngineResponseDTO;
import com.backend.topcariving.domain.dto.option.response.model.ModelResponseDTO;
import com.backend.topcariving.domain.dto.option.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;

@SpringBootTest
@Transactional
public class TrimServiceTest extends TestSupport {

	@Autowired
	private MyCarRepository myCarRepository;

	@Autowired
	private CarArchivingRepository carArchivingRepository;

	@Autowired
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
		}

		@Test
		void 모델이_제대로_저장되고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 1L;
			Long carOptionId = 1L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, null);

			// when
			Long archivingId = trimService.saveModel(userId, selectOptionRequestDTO);

			// then
			final boolean isExists = carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId);
			Assertions.assertThat(isExists).isTrue();
		}

		@Test
		void 모델_변경_시_아카이빙_아이디가_있을_경우_값이_업데이트_되어야_한다() {
			// given
			Long userId = 1L;
			Long carOptionId = 2L;
			Long archivingId = 1L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId,
				archivingId);

			// when
			final Long returnedArchivingId = trimService.saveModel(userId, selectOptionRequestDTO);

			// then
			final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);
			MyCar myCar = myCars.stream().filter(findCar -> findCar.getCarOptionId().equals(carOptionId)).findFirst()
				.orElse(null);
			Assertions.assertThat(myCar.getCarOptionId()).isEqualTo(carOptionId);
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
		}

		@Test
		void 엔진이_제대로_저장되고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			Long carOptionId = 5L;
			Long archivingId = 3L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(3L)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = trimService.saveOption(userId, selectOptionRequestDTO, CategoryDetail.ENGINE);

			// then
			Assertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> findMyCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId);
			Assertions.assertThat(findMyCar).isPresent();
		}

		@Test
		void 엔진_변경_시_이미_값이_있을_경우_값이_업데이트_되어야_한다() {
			// given
			Long userId = 1L;
			Long carOptionId = 6L;
			Long archivingId = 1L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId,
				archivingId);

			// when
			final Long returnedArchivingId = trimService.saveOption(userId, selectOptionRequestDTO, CategoryDetail.ENGINE);

			// then
			final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);
			MyCar myCar = myCars.stream()
				.filter(findCar -> Objects.equals(findCar.getCarOptionId(), carOptionId)).findFirst()
				.orElse(null);
			Assertions.assertThat(myCar.getCarOptionId()).isEqualTo(carOptionId);
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
		}

		@Test
		void 바디타입이_제대로_저장되고_아카이빙_아이디가_반환되어야_한다() {
			// given
			Long userId = 3L;
			Long carOptionId = 7L;
			Long archivingId = 3L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId,
				archivingId);
			MyCar myCar = MyCar.builder()
				.archivingId(3L)
				.carOptionId(1L)
				.build();
			myCarRepository.save(myCar);

			// when
			Long savedArchivingId = trimService.saveOption(userId, selectOptionRequestDTO, CategoryDetail.BODY_TYPE);

			// then
			Assertions.assertThat(savedArchivingId).isEqualTo(archivingId);
			Optional<MyCar> findMyCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId);
			Assertions.assertThat(findMyCar).isPresent();
		}

		@Test
		void 바디타입_변경_시_이미_값이_있을_경우_값이_업데이트_되어야_한다() {
			// given
			Long userId = 1L;
			Long carOptionId = 8L;
			Long archivingId = 1L;
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId,
				archivingId);

			// when
			final Long returnedArchivingId = trimService.saveOption(userId, selectOptionRequestDTO, CategoryDetail.BODY_TYPE);

			// then
			final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);
			MyCar myCar = myCars.stream().filter(findCar -> findCar.getCarOptionId() == carOptionId).findFirst()
				.orElse(null);
			Assertions.assertThat(myCar.getCarOptionId()).isEqualTo(carOptionId);
		}
	}
}
