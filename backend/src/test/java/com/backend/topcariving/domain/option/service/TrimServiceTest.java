package com.backend.topcariving.domain.option.service;

import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.topcariving.domain.archive.exception.InvalidAuthorityException;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.dto.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.option.exception.InvalidCarOptionIdException;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;
import com.backend.topcariving.domain.option.repository.EngineDetailRepository;
import com.backend.topcariving.domain.option.repository.ModelPhotoRepository;

@ExtendWith(MockitoExtension.class)
class TrimServiceTest {

	@Mock
	private CarOptionRepository carOptionRepository;
	@Mock
	private ModelPhotoRepository modelPhotoRepository;

	@Mock
	private CarArchivingRepository carArchivingRepository;

	@Mock
	private MyCarRepository myCarRepository;

	@Mock
	private EngineDetailRepository engineDetailRepository;

	@InjectMocks
	private TrimService trimService;

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

	@Test
	void 엔진을_선택시_내_차가_아닌_차에_옵션을_추가하면_에러가_발생한다() {
		// given
		given(carArchivingRepository.existsByUserIdAndArchivingId(1L, 22L))
			.willReturn(false);

		final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(1L, 5L, 22L);

		// when, then
		Assertions.assertThatThrownBy(() -> trimService.saveEngine(selectOptionRequestDTO))
			.isInstanceOf(InvalidAuthorityException.class);
	}

	@Test
	void 엔진을_선택시_엔진이_아닌_옵션을_선택하면_에러가_발생한다() {
		// given
		given(carArchivingRepository.existsByUserIdAndArchivingId(1L, 22L))
			.willReturn(true);
		given(carOptionRepository.existsByCarOptionIdAndCategoryDetail(1L, "엔진"))
			.willReturn(false);

		final SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(1L, 1L, 22L);

		// when, then
		Assertions.assertThatThrownBy(() -> trimService.saveEngine(selectOptionRequestDTO))
			.isInstanceOf(InvalidCarOptionIdException.class);
	}
}