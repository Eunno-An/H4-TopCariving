package com.backend.topcariving.domain.option.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.option.entity.CarOption;

@SpringBootTest
@Transactional
class CarOptionRepositoryTest {

	@Autowired
	private CarOptionRepository carOptionRepository;

	@Test
	void 여러건의_아이디에_대한_조회가_제대로_동작하는지() {
		// given
		List<Long> ids = List.of(1L, 2L);

		// when
		final List<CarOption> result = carOptionRepository.findByIds(ids);

		// then
		Assertions.assertThat(result).hasSize(2);
	}
}