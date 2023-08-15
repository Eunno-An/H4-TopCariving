package com.backend.topcariving.domain.option.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.entity.CategoryDetail;

@JdbcTest
class CarOptionRepositoryTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CarOptionRepositoryImpl carOptionRepository;

	@BeforeEach
	void setUp() {
		carOptionRepository = new CarOptionRepositoryImpl(jdbcTemplate);
	}

	@Test
	void 여러건의_아이디에_대한_조회가_제대로_동작하는지() {
		// given
		List<Long> ids = List.of(1L, 2L);

		// when
		final List<CarOption> result = carOptionRepository.findByIds(ids);

		// then
		Assertions.assertThat(result).hasSize(2);
	}

	@Test
	void findByCategoryDetailAndParentOptionIdIsNull() {
		// given
		String categoryDetail = CategoryDetail.N_PERFORMANCE.getName();

		// when
		final List<CarOption> carOptions = carOptionRepository.findByCategoryDetailAndParentOptionIdIsNull(
			categoryDetail);

		// then
		Assertions.assertThat(carOptions).as("반환값 갯수 확인").hasSize(3);
	}


}