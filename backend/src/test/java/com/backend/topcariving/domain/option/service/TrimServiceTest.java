package com.backend.topcariving.domain.option.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.topcariving.domain.option.dto.model.ModelResponseDTO;
import com.backend.topcariving.domain.option.entity.ModelPhoto;
import com.backend.topcariving.domain.option.entity.Option;
import com.backend.topcariving.domain.option.repository.ModelPhotoRepository;
import com.backend.topcariving.domain.option.repository.OptionRepository;

@ExtendWith(MockitoExtension.class)
class TrimServiceTest {

	@Mock
	private OptionRepository optionRepository;
	@Mock
	private ModelPhotoRepository modelPhotoRepository;

	@InjectMocks
	private TrimService trimService;

	@Test
	void 모델의_종류를_반환해야한다() {
		// given
		final Option option = new Option(1L, "카테고리", "카테고리 디테일", "옵션이름", "옵션디테일", 1000, "포토URL", null);
		given(optionRepository.findByCategoryDetail(anyString()))
			.willReturn(List.of(option));

		final ModelPhoto modelPhoto1 = new ModelPhoto(1L, "포토 이름 설명", "svg 경로", "photo 경로", 1L);
		final ModelPhoto modelPhoto2 = new ModelPhoto(2L, "포토 이름 설명2", "svg2 경로", "photo2 경로", 2L);
		given(modelPhotoRepository.findAllByCarOptionId(1L))
			.willReturn(List.of(modelPhoto1, modelPhoto2));

		// when
		final List<ModelResponseDTO> models = trimService.getModels();

		// then
		ModelResponseDTO model = models.get(0);
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(model.getCarOptionId()).as("옵션 아이디 테스트").isEqualTo(1L);
		softAssertions.assertThat(model.getOptionName()).as("옵션 이름 테스트").isEqualTo("옵션이름");
		softAssertions.assertThat(model.getPrice()).as("옵션 가격 테스트").isEqualTo(1000);
		softAssertions.assertThat(models).as("models의 갯수 테스트").hasSize(1);
		softAssertions.assertThat(model.getPhotos()).as("photos의 크기 테스트").hasSize(2);
		softAssertions.assertThat(model.getPhotos().get(0).getContent()).as("Photo의 attirbute가 제대로 저장되었는지 테스트").isEqualTo(modelPhoto1.getContent());
		softAssertions.assertAll();
	}
}