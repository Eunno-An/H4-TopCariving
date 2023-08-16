package com.backend.topcariving.domain.archive.repository;

import static com.backend.topcariving.domain.archive.entity.ArchivingType.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.entity.ArchivingType;
import com.backend.topcariving.domain.archive.entity.CarArchiving;

@JdbcTest
class CarArchivingRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CarArchivingRepositoryImpl carArchivingRepository;

	@BeforeEach
	void setUp() {
		carArchivingRepository = new CarArchivingRepositoryImpl(jdbcTemplate);
	}

	@Test
	void save() {
		// given
		CarArchiving carArchiving = CarArchiving.builder()
			.archivingType(ArchivingType.MAKE.getType())
			.isComplete(false)
			.userId(1L)
			.isAlive(false)
			.build();

		// when
		carArchiving = carArchivingRepository.save(carArchiving);

		// then
		final CarArchiving findCarArchiving = carArchivingRepository.findById(carArchiving.getArchivingId())
			.get();
		softAssertions.assertThat(carArchiving.getArchivingId()).as("아카이빙 아이디 검증").isEqualTo(findCarArchiving.getArchivingId());
		softAssertions.assertThat(carArchiving.getUserId()).as("사용자 아이디 검증").isEqualTo(findCarArchiving.getUserId());
		softAssertions.assertThat(carArchiving.getIsAlive()).as("저장 여부 검증").isEqualTo(findCarArchiving.getIsAlive());
		softAssertions.assertThat(carArchiving.getIsComplete()).as("만들기 완료 여부 검증").isEqualTo(findCarArchiving.getIsComplete());
		softAssertions.assertThat(carArchiving.getArchivingType()).as("아카이빙 타입 검증").isEqualTo(findCarArchiving.getArchivingType());
	}

	@Test
	void existsByUserIdAndArchivingId() {
		// given
		Long userId = 1L;
		Long archivingId = 1L;

		// when
		final boolean isExists = carArchivingRepository.existsByUserIdAndArchivingId(userId, archivingId);

		// then
		Assertions.assertThat(isExists).isTrue();
	}

	@Test
	void updateIsCompleteByArchivingId() {
		// given
		CarArchiving carArchiving = CarArchiving.builder()
			.archivingType(ArchivingType.MAKE.getType())
			.isComplete(false)
			.userId(1L)
			.isAlive(false)
			.build();
		carArchiving = carArchivingRepository.save(carArchiving);

		// when
		carArchivingRepository.updateIsCompleteByArchivingId(carArchiving.getArchivingId(), true);

		// then
		final CarArchiving findCarArchiving = carArchivingRepository.findById(carArchiving.getArchivingId()).get();
		Assertions.assertThat(findCarArchiving.getIsComplete()).isTrue();
	}

	@Test
	void findByCarOptionIdsAndArchivingTypes() {
		// given, when
		List<CarArchiving> carArchivings = carArchivingRepository.findByCarOptionIdsAndArchivingTypes(
			List.of(103L, 110L), List.of(DRIVE.getType(), BUY.getType())
		);

		// then
		softAssertions.assertThat(carArchivings.get(0).getArchivingId()).as("첫 번째 아카이빙의 아카이빙 아이디 검증").isEqualTo(1L);
		softAssertions.assertThat(carArchivings.get(0).getArchivingType()).as("첫 번째 아카이빙의 아카이빙 타입 검증").isEqualTo("시승");
		softAssertions.assertThat(carArchivings.get(1).getArchivingId()).as("두 번째 아카이빙의 아카이빙 아이디 검증").isEqualTo(3L);
		softAssertions.assertThat(carArchivings.get(1).getArchivingType()).as("두 번째 아카이빙의 아카이빙 타입 검증").isEqualTo("시승");
	}

	@Test
	void findByArchivingTypes() {
		// given, when
		List<String> types = List.of(ArchivingType.DRIVE.getType(), BUY.getType());
		List<CarArchiving> carArchivings = carArchivingRepository.findByArchivingTypes(types);

		// then
		softAssertions.assertThat(carArchivings.get(0).getArchivingId()).as("1이 반환되어야 함").isEqualTo(1L);
		softAssertions.assertThat(carArchivings.get(0).getArchivingType()).as("'시승'이 반환되어야 함'").isEqualTo("시승");
		softAssertions.assertThat(carArchivings.get(1).getArchivingId()).as("2가 반환되어야 함").isEqualTo(2L);
		softAssertions.assertThat(carArchivings.get(1).getArchivingType()).as("'구매'가 반환되어야 함'").isEqualTo("구매");
		softAssertions.assertThat(carArchivings.get(2).getArchivingId()).as("3이 반환되어야 함").isEqualTo(3L);
		softAssertions.assertThat(carArchivings.get(2).getArchivingType()).as("'시승'이 반환되어야 함'").isEqualTo("시승");
	}

}