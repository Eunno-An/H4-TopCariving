package com.backend.topcariving.domain.option.service;

import static org.mockito.BDDMockito.*;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.option.dto.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.option.entity.CategoryDetail;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

@ExtendWith(MockitoExtension.class)
class TrimServiceTest {

	@Mock
	private CarOptionRepository carOptionRepository;

	@Mock
	private CarArchivingRepository carArchivingRepository;

	@InjectMocks
	private TrimService trimService;

	@ParameterizedTest
	@MethodSource("generateOptionSelectedData")
	void 해당하는_옵션_선택시_내_차가_아닌_차에_옵션을_선택한다면_에러가_발생한다(Long archivingId, Long carOptionId, CategoryDetail categoryDetail) {
		// given
		given(carArchivingRepository.existsByUserIdAndArchivingId(1L, archivingId))
			.willReturn(false);
		final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(1L, carOptionId, archivingId);

		// when, then
		Assertions.assertThatThrownBy(() -> trimService.saveOption(selectOptionRequestDTO, categoryDetail))
			.isInstanceOf(InvalidAuthorityException.class);
	}

	@ParameterizedTest
	@MethodSource("generateArchivingIdToSelectedData")
	void 옵션_선택시_해당되는_옵션이_아닌_옵션을_선택하면_에러가_발생한다(Long archivingId, CategoryDetail categoryDetail) {
		// given
		given(carArchivingRepository.existsByUserIdAndArchivingId(1L,archivingId))
			.willReturn(true);
		given(carOptionRepository.existsByCarOptionIdAndCategoryDetail(1L, categoryDetail.getName()))
			.willReturn(false);
		final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(1L, 1L, archivingId);

		// when, then
		Assertions.assertThatThrownBy(() -> trimService.saveOption(selectOptionRequestDTO, categoryDetail))
			.isInstanceOf(InvalidCarOptionIdException.class);
	}

	@Nested
	@DisplayName("Model 테스트")
	class Model {
		@Test
		void 모델을_선택시_모델이_아닌_옵션을_선택하면_에러가_발생한다() {
			// given
			given(carOptionRepository.existsByCarOptionIdAndCategoryDetail(6L, "모델"))
				.willReturn(false);
			final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(1L, 6L, null);

			// when, then
			Assertions.assertThatThrownBy(() -> trimService.saveModel(selectOptionRequestDTO))
				.isInstanceOf(InvalidCarOptionIdException.class);
		}
	}

	static Stream<Arguments> generateOptionSelectedData() {
		return Stream.of(
			Arguments.of(22L, 6L, CategoryDetail.ENGINE),
			Arguments.of(22L, 6L, CategoryDetail.BODY_TYPE),
			Arguments.of(22L, 6L, CategoryDetail.DRIVING_METHOD),
			Arguments.of(22L, 6L, CategoryDetail.EXTERIOR_COLOR),
			Arguments.of(22L, 6L, CategoryDetail.INTERIOR_COLOR),
			Arguments.of(22L, 6L, CategoryDetail.INTERIOR_COLOR_IMAGE),
			Arguments.of(22L, 6L, CategoryDetail.POWER_TRAIN),
			Arguments.of(22L, 6L, CategoryDetail.SAFETY),
			Arguments.of(22L, 6L, CategoryDetail.EXTERIOR),
			Arguments.of(22L, 6L, CategoryDetail.INTERIOR),
			Arguments.of(22L, 6L, CategoryDetail.SEAT),
			Arguments.of(22L, 6L, CategoryDetail.CONVENIENCE),
			Arguments.of(22L, 6L, CategoryDetail.MULTI_MEDIA),
			Arguments.of(22L, 6L, CategoryDetail.SELECTED),
			Arguments.of(22L, 6L, CategoryDetail.H_GENUINE_ACCESSORIES),
			Arguments.of(22L, 6L, CategoryDetail.N_PERFORMANCE)
		);
	}


	static Stream<Arguments> generateArchivingIdToSelectedData() {
		return Stream.of(
			Arguments.of(22L, CategoryDetail.ENGINE),
			Arguments.of(22L, CategoryDetail.BODY_TYPE),
			Arguments.of(22L, CategoryDetail.DRIVING_METHOD),
			Arguments.of(22L, CategoryDetail.EXTERIOR_COLOR),
			Arguments.of(22L, CategoryDetail.INTERIOR_COLOR),
			Arguments.of(22L, CategoryDetail.INTERIOR_COLOR_IMAGE),
			Arguments.of(22L, CategoryDetail.POWER_TRAIN),
			Arguments.of(22L, CategoryDetail.SAFETY),
			Arguments.of(22L, CategoryDetail.EXTERIOR),
			Arguments.of(22L, CategoryDetail.INTERIOR),
			Arguments.of(22L, CategoryDetail.SEAT),
			Arguments.of(22L, CategoryDetail.CONVENIENCE),
			Arguments.of(22L, CategoryDetail.MULTI_MEDIA),
			Arguments.of(22L, CategoryDetail.SELECTED),
			Arguments.of(22L, CategoryDetail.H_GENUINE_ACCESSORIES),
			Arguments.of(22L, CategoryDetail.N_PERFORMANCE)
		);
	}
}