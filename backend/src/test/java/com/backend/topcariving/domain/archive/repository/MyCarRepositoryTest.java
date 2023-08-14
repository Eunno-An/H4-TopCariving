package com.backend.topcariving.domain.archive.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.option.dto.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.option.entity.CategoryDetail;

@JdbcTest
class MyCarRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private MyCarRepositoryImpl myCarRepository;

	@BeforeEach
	void setUp() {
		myCarRepository = new MyCarRepositoryImpl(jdbcTemplate);
	}

	@Test
	void save() {
		// given
		MyCar myCar = MyCar.builder()
			.archivingId(1L)
			.carOptionId(3L)
			.build();

		// when
		myCar = myCarRepository.save(myCar);

		// then
		final MyCar findMyCar = myCarRepository.findById(myCar.getMyCarId()).get();
		Assertions.assertThat(myCar).usingRecursiveComparison().isEqualTo(findMyCar);
	}

	@Test
	void findByArchivingIdAndCarOptionId() {
		// given
		Long archivingId = 1L;
		Long carOptionId = 1L;

		// when
		final MyCar myCar = myCarRepository.findByArchivingIdAndCarOptionId(archivingId, carOptionId).get();

		// then
		softAssertions.assertThat(myCar.getCarOptionId()).as("옵션 ID 검증").isEqualTo(carOptionId);
		softAssertions.assertThat(myCar.getMyCarId()).as("마이 카 ID 검증").isEqualTo(1L);
		softAssertions.assertThat(myCar.getArchivingId()).as("아카이빙 ID 검증").isEqualTo(archivingId);
	}

	@Test
	void findByArchivingId() {
		// given
		Long archivingId = 1L;

		// when
		final List<MyCar> myCars = myCarRepository.findByArchivingId(archivingId);

		// then
		Assertions.assertThat(myCars).hasSize(8);
	}

	@Test
	void findOptionSummaryByArchivingId() {
		// given
		Long archivingId = 1L;

		// when
		final List<OptionSummaryDTO> OptionSummaryDTOs = myCarRepository.findOptionSummaryByArchivingId(
			archivingId);

		// then
		final OptionSummaryDTO optionSummary = OptionSummaryDTOs.stream()
			.filter(optionSummaryDTO -> optionSummaryDTO.getCarOptionId() == 1L)
			.findFirst()
			.orElse(null);
		softAssertions.assertThat(optionSummary.getCategory()).as("카테고리 테스트").isEqualTo("트림");
		softAssertions.assertThat(optionSummary.getCategoryDetail()).as("카테고리 세부정보 테스트").isEqualTo("모델");
		softAssertions.assertThat(optionSummary.getName()).as("이름 테스트").isEqualTo("Le Blanc");

	}

	@Test
	void deleteByArchivingIdAndCategoryDetail() {
		// given
		Long archivingId = 1L;
		String categoryDetail = CategoryDetail.MODEL.getName();

		// when
		myCarRepository.deleteByArchivingIdAndCategoryDetail(archivingId, categoryDetail);

		// then
		final Optional<MyCar> findMyCar = myCarRepository.findById(1L);
		Assertions.assertThat(findMyCar).isEmpty();
	}

	// @Test
	// void saveMultipleData() {
	// 	// given
	// 	MyCar myCar1 = MyCar.builder()
	// 		.archivingId(1L)
	// 		.carOptionId(113L)
	// 		.build();
	// 	MyCar myCar2 = MyCar.builder()
	// 		.archivingId(1L)
	// 		.carOptionId(130L)
	// 		.build();
	//
	// 	// when
	// 	myCarRepository.saveMultipleData(List.of(myCar1, myCar2));
	//
	// 	// then
	// 	final Optional<MyCar> findMyCar1 = myCarRepository.findByArchivingIdAndCarOptionId(1L, 113L);
	// 	softAssertions.assertThat(findMyCar1).as("1번 저장 값이 유효한지").isPresent();
	//
	// 	final Optional<MyCar> findMyCar2 = myCarRepository.findByArchivingIdAndCarOptionId(1L, 130L);
	// 	softAssertions.assertThat(findMyCar2).as("2번 저장 값이 유효한지").isPresent();
	// }


	@Test
	void updateCarOptionIdByArchivingIdAndCategoryDetail() {
		// given
		Long archivingId = 1L;
		Long carOptionId = 2L;
		
		// when
		myCarRepository.updateCarOptionIdByArchivingIdAndCategoryDetail(archivingId, carOptionId, CategoryDetail.MODEL.getName());
		
		// then
		final MyCar findMyCar = myCarRepository.findById(1L).get();
		softAssertions.assertThat(findMyCar.getCarOptionId()).as("차 옵션 ID 테스트").isEqualTo(2L);
		softAssertions.assertThat(findMyCar.getArchivingId()).as("아카이빙 ID 테스트").isEqualTo(1L);

	}
}