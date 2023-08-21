package com.backend.topcariving.domain.option.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.domain.dto.option.response.color.BothColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.InteriorColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.service.option.ColorService;

@SpringBootTest
@Transactional
class ColorServiceIntegralTest {

	@Autowired
	private ColorService colorService;

	@Nested
	@DisplayName("외장 색상 테스트")
	class ExteriorColor {
		@Test
		void 외장_색상의_정보가_제대로_반환_되어야_한다() {
			// given
			final ExteriorColorResponseDTO result = getExteriorColorResponseDTO();

			// when
			final List<ExteriorColorResponseDTO> exteriorColors = colorService.getExteriorColors();

			// then
			final ExteriorColorResponseDTO exteriorColorResponseDTO = exteriorColors.get(0);
			Assertions.assertThat(exteriorColorResponseDTO).usingRecursiveComparison().isEqualTo(result);
		}
	}

	@Nested
	@DisplayName("내장 색상 테스트")
	class InteriorColor {
		@Test
		void 내장_색상의_정보가_제대로_반환_되어야_한다() {
			// given
			final InteriorColorResponseDTO result = getInteriorColorResponseDTO();

			// when
			final List<InteriorColorResponseDTO> interiorColors = colorService.getInteriorColors();

			// then
			final InteriorColorResponseDTO interiorColorResponseDTO = interiorColors.get(0);
			Assertions.assertThat(interiorColorResponseDTO).usingRecursiveComparison().isEqualTo(result);
		}
	}

	@Nested
	@DisplayName("내장 외장 색상 테스트")
	class BothColor {
		@Test
		void 외장_내장_색상의_정보가_제대로_반환_되어야_한다() {
			// given
			final ExteriorColorResponseDTO exteriorResult= getExteriorColorResponseDTO();
			final InteriorColorResponseDTO interiorResult = getInteriorColorResponseDTO();

			// when
			final BothColorResponseDTO bothResponseDTO = colorService.getBothResponseDTO();

			// then
			final List<ExteriorColorResponseDTO> exteriorColorResponses = bothResponseDTO.getExteriorColorResponses();
			final ExteriorColorResponseDTO exteriorColorResponseDTO = exteriorColorResponses.get(0);
			Assertions.assertThat(exteriorColorResponseDTO).usingRecursiveComparison().isEqualTo(exteriorResult);

			final List<InteriorColorResponseDTO> interiorColorResponses = bothResponseDTO.getInteriorColorResponses();
			final InteriorColorResponseDTO interiorColorResponseDTO = interiorColorResponses.get(0);
			Assertions.assertThat(interiorColorResponseDTO).usingRecursiveComparison().isEqualTo(interiorResult);
		}
	}

	private InteriorColorResponseDTO getInteriorColorResponseDTO() {
		final List<TagResponseDTO> tagResponseDTOS = List.of(new TagResponseDTO("깔끔해요\uD83D\uDC4D"),
			new TagResponseDTO("마음에 들어요\uD83D\uDE04"),
			new TagResponseDTO("무난한 색상\uD83D\uDE19"),
			new TagResponseDTO("밝고화사해요✨"),
			new TagResponseDTO("청소하기 좋아요\uD83E\uDDF9"));
		return InteriorColorResponseDTO.builder()
			.carOptionId(17L)
			.optionName("퀄팅천연(블랙)")
			.photoUrl("https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/black_internal.png")
			.colorUrl("https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/black.png")
			.price(0)
			.tagResponses(tagResponseDTOS)
			.build();
	}

	private ExteriorColorResponseDTO getExteriorColorResponseDTO() {
		final List<TagResponseDTO> tagResponseDTOS = List.of(new TagResponseDTO("깔끔해요\uD83D\uDC4D"),
			new TagResponseDTO("모두가 좋아하는 색상\uD83E\uDD70"),
			new TagResponseDTO("무게감있는 톤\uD83C\uDF11"),
			new TagResponseDTO("밝고화사해요✨"),
			new TagResponseDTO("트렌디해요\uD83D\uDE0E"));
		return ExteriorColorResponseDTO.builder()
			.carOptionId(11L)
			.optionName("어비스블랙펄")
			.colorUrl("https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/black.png")
			.price(0)
			.tagResponses(tagResponseDTOS)
			.build();
	}
}
