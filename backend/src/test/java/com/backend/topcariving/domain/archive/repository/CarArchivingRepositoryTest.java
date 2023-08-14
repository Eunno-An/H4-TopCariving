package com.backend.topcariving.domain.archive.repository;

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
		softAssertions.assertThat(carArchiving.getArchivingId()).isEqualTo(findCarArchiving.getArchivingId());
		softAssertions.assertThat(carArchiving.getUserId()).isEqualTo(findCarArchiving.getUserId());
		softAssertions.assertThat(carArchiving.getIsAlive()).isEqualTo(findCarArchiving.getIsAlive());
		softAssertions.assertThat(carArchiving.getIsComplete()).isEqualTo(findCarArchiving.getIsComplete());
		softAssertions.assertThat(carArchiving.getArchivingType()).isEqualTo(findCarArchiving.getArchivingType());
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
}