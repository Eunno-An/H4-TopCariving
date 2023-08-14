package com.backend.topcariving.domain.option.service;

import static org.mockito.BDDMockito.*;

import java.util.List;

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
import com.backend.topcariving.domain.option.dto.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.option.entity.CarOption;
import com.backend.topcariving.domain.option.exception.InvalidCategoryException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

@ExtendWith(MockitoExtension.class)
class EstimationServiceTest {

	@Mock
	private CarArchivingRepository carArchivingRepository;

	@Mock
	private CarOptionRepository carOptionRepository;

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

	@Nested
	@DisplayName("요약 보기")
	class Summary {
		@Test
		void 수정시_같은_카테고리만_수정이_가능하다() {
			// given
			List<Long> ids = List.of(1L, 110L);
			final EstimationChangeRequestDTO request = EstimationChangeRequestDTO.builder()
				.optionIds(ids)
				.userId(1L)
				.archivingId(1L)
				.build();
			List<CarOption> result = List.of(
				new CarOption(1L, "트림", "모델", "르블랑","설명", 0, "photo_url", null),
				new CarOption(110L, "선택품목", "상세 품목", "컴포트", "설명", 0, "photo_url", null));

			given(carArchivingRepository.existsByUserIdAndArchivingId(1L, 1L))
				.willReturn(true);
			given(carOptionRepository.findByIds(ids))
				.willReturn(result);

			// when, then
			Assertions.assertThatThrownBy(() -> estimationService.changeOptions(request))
					.isInstanceOf(InvalidCategoryException.class);
		}
	}

}