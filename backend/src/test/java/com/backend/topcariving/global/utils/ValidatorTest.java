package com.backend.topcariving.global.utils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.repository.archive.CarArchivingRepository;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.exception.InvalidArchivingIdException;
import com.backend.topcariving.domain.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.exception.InvalidCategoryException;
import com.backend.topcariving.domain.repository.option.CarOptionRepository;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

	@Mock
	private CarArchivingRepository carArchivingRepository;

	@Mock
	private CarOptionRepository carOptionRepository;

	@InjectMocks
	private Validator validator;

	@Test
	void 유저는_자신의_아카이빙_아이디만_수정_할_수_있다() {
		// given
		given(carArchivingRepository.existsByUserIdAndArchivingId(anyLong(), anyLong()))
			.willReturn(false);

		// when, then
		assertThatThrownBy(() -> validator.verifyCarArchiving(1L, 1L))
			.isInstanceOf(InvalidAuthorityException.class);
	}

	@ParameterizedTest
	@MethodSource("generateCarOptionIdToSelectedData")
	void 유저는_API에서_지원_가능한_옵션만_변경이_가능하다(Long carOptionId, CategoryDetail categoryDetail) {
		// given
		given(carOptionRepository.existsByCarOptionIdAndCategoryDetail(carOptionId, categoryDetail))
			.willReturn(false);

		// when, then
		Assertions.assertThatThrownBy(() -> validator.verifyCarOptionId(categoryDetail, carOptionId))
			.isInstanceOf(InvalidCarOptionIdException.class);
	}

	@Test
	void 선택_옵션에서_선택_옵션_카테고리가_아닌_옵션이_존재하면_오류가_발생한다() {
		// given
		CarOption carOption1 = new CarOption(1L, Category.CHOICE, CategoryDetail.SELECTED,
			"선택옵션1", "선택옵션 설명1", 0, "사진경로1", null);
		CarOption carOption2 = new CarOption(2L, Category.CHOICE, CategoryDetail.N_PERFORMANCE,
			"N 퍼포먼스1", "N 퍼포먼스 설명1", 0, "사진경로2", null);

		// when, then
		assertThatThrownBy(() -> validator.verifySameCategory(List.of(carOption1, carOption2), CategoryDetail.SELECTED))
			.isInstanceOf(InvalidCategoryException.class);
	}

	@Test
	void 아카이빙_아이디가_존재하지_않으면_오류가_발생한다() {
		// given
		given(carArchivingRepository.existsByArchivingId(21L)).willReturn(false);

		// when, then
		Assertions.assertThatThrownBy(() -> validator.verifyArchivingId(21L))
			.isInstanceOf(InvalidArchivingIdException.class);
	}

	static Stream<Arguments> generateCarOptionIdToSelectedData() {
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