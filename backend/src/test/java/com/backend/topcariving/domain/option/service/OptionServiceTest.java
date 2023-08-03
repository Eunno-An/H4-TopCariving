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

import com.backend.topcariving.domain.option.dto.OptionResponseDTO;
import com.backend.topcariving.domain.option.entity.Option;
import com.backend.topcariving.domain.option.repository.OptionRepository;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

	@Mock
	private OptionRepository optionRepository;

	@InjectMocks
	private OptionService optionService;

	@Test
	void 모델의_종류를_반환해야한다() {
		// given
		final Option option = new Option(1L, "카테고리", "카테고리 디테일", "옵션이름", "옵션디테일", 1000, "포토URL", null);
		given(optionRepository.findByCategoryDetail(anyString()))
			.willReturn(List.of(option));

		// when
		final List<OptionResponseDTO> models = optionService.getModels();

		// then
		OptionResponseDTO model = models.get(0);
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(model.getCarOptionId()).as("옵션 아이디 테스트").isEqualTo(1L);
		softAssertions.assertThat(model.getCategory()).as("카테고리 테스트").isEqualTo("카테고리");
		softAssertions.assertThat(model.getCategoryDetail()).as("카테고리 디테일 테스트").isEqualTo("카테고리 디테일");
		softAssertions.assertThat(model.getOptionName()).as("옵션 이름 테스트").isEqualTo("옵션이름");
		softAssertions.assertThat(model.getPrice()).as("옵션 가격 테스트").isEqualTo(1000);
		softAssertions.assertThat(model.getPhotoUrl()).as("사진 URL 테스트").isEqualTo("포토URL");
		softAssertions.assertThat(models).hasSize(1);
		softAssertions.assertAll();
	}
}