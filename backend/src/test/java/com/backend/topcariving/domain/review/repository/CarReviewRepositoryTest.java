package com.backend.topcariving.domain.review.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.domain.review.entity.CarReview;

@JdbcTest
class CarReviewRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CarReviewRepository carReviewRepository;

	@BeforeEach
	void setUp() {
		carReviewRepository = new CarReviewRepositoryImpl(jdbcTemplate);
	}

	@Test
	void findByMyCarId() {
		// given
		Long myCarId = 9L;

		// when
		CarReview carReview = carReviewRepository.findByMyCarId(myCarId).get();

		// then
		Assertions.assertThat(carReview.getReview()).isEqualTo("너무 좋아요");
	}

}