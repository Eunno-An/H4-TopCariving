package com.backend.topcariving.domain.option.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.option.dto.response.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

@SpringBootTest
@Transactional
class ColorServiceIntegralTest {

	@Autowired
	private ColorService colorService;

	@Test
	void 외장_색상의_정보가_제대로_반환_되어야_한다() {
		// given
		final List<TagResponseDTO> tagResponseDTOS = List.of(new TagResponseDTO("흔하지 않은 색\uD83E\uDD2D"),
			new TagResponseDTO("트렌디해요😎"),
			new TagResponseDTO("밝고화사해요✨"));
		final ExteriorColorResponseDTO result = ExteriorColorResponseDTO.builder()
			.carOptionId(11L)
			.optionName("어비스블랙펄")
			.colorUrl("https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/black.png")
			.price(0)
			.tagResponses(tagResponseDTOS)
			.build();

		// when
		final List<ExteriorColorResponseDTO> exteriorColors = colorService.getExteriorColors();

		// then
		final ExteriorColorResponseDTO exteriorColorResponseDTO = exteriorColors.get(0);
		Assertions.assertThat(exteriorColorResponseDTO).usingRecursiveComparison().isEqualTo(result);
	}

}