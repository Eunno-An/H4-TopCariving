package com.backend.topcariving.domain.review.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

@SpringBootTest
@Transactional
class TagReviewRepositoryTest extends TestSupport {

	@Autowired
	private TagReviewRepository tagReviewRepository;

	@Test
	void 태그에서_가장_많은_태그에_대한_정보를_3건_가져오는_쿼리가_제대로_동작하는가() {
		// given
		Long carOptionId = 110L;
		String result = "가격이 합리적이에요\uD83D\uDC4D";
		// when
		final List<TagResponseDTO> tagResponseDTOS = tagReviewRepository.findTagResponseDTOByCarOptionId(
			carOptionId);

		// then
		softAssertions.assertThat(tagResponseDTOS).as("태그가 정말로 3건을 가져오는지").hasSize(3);
		final TagResponseDTO tagResponseDTO = tagResponseDTOS.get(0);
		softAssertions.assertThat(tagResponseDTO.getTagContent()).as("1등의 Content 확인").isEqualTo(result);
	}
}