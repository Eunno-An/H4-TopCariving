package com.backend.topcariving.domain.option.service;

import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;

@ExtendWith(MockitoExtension.class)
class EstimationServiceTest {

	@Mock
	private CarArchivingRepository carArchivingRepository;

	@InjectMocks
	private EstimationService estimationService;

	@Nested
	@DisplayName("내 차 만들기 결과 조회")
	class MyCarCreation {
		@Test
		void 내_차_만들기_결과_조회_시_내_차가_아닌_차를_조회하면_에러가_발생한다() {
			// given
			given(carArchivingRepository.existsByUserIdAndArchivingId(1L, 22L))
				.willReturn(false);

			// when, then
			Assertions.assertThatThrownBy(() -> estimationService.getArchivingResult(1L, 22L))
				.isInstanceOf(InvalidAuthorityException.class);
		}
	}

}