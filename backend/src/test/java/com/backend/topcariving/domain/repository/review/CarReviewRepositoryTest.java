package com.backend.topcariving.domain.repository.review;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.dto.archive.response.TotalReviewDTO;
import com.backend.topcariving.domain.entity.review.CarReview;
import com.backend.topcariving.domain.repository.review.CarReviewRepository;
import com.backend.topcariving.domain.repository.review.implement.CarReviewRepositoryImpl;

@JdbcTest
class CarReviewRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CarReviewRepository carReviewRepository;

	@BeforeEach
	void setUp() {
		carReviewRepository = new CarReviewRepositoryImpl(jdbcTemplate);
	}

	@Test
	void findByArchivingId() {
		// given
		Long archivingId = 1L;

		// when
		CarReview carReview = carReviewRepository.findByArchivingId(archivingId).get();

		// then
		Assertions.assertThat(carReview.getReview()).isEqualTo("너무 좋아요");
	}

	@Test
	void findTotalReviewDTOsByArchivingIds() {
		// given
		List<Long> archivingIds = List.of(1L, 2L);

		// when
		Map<Long, TotalReviewDTO> result = carReviewRepository.findTotalReviewDTOsByArchivingIds(
			archivingIds);

		// then
		softAssertions.assertThat(result).as("가져온 데이터의 크기").hasSize(1);
		TotalReviewDTO totalReviewDTO = result.get(1L);
		softAssertions.assertThat(totalReviewDTO.getTags()).as("선택한 태그들의 크기는 3이다").hasSize(3);
		softAssertions.assertThat(totalReviewDTO.getReview()).as("전체 리뷰").isEqualTo("너무 좋아요");
	}

	@Test
	void findTotalReviewDTOsByArchivingIdsNoResult() {
		// given
		List<Long> archivingIds = List.of(30L, 33L, 36L);

		// when
		Map<Long, TotalReviewDTO> result = carReviewRepository.findTotalReviewDTOsByArchivingIds(archivingIds);

		// then
		Assertions.assertThat(result).hasSize(0);
	}

}