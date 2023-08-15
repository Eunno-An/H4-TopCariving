package com.backend.topcariving.domain.review.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;

@JdbcTest
class TagReviewRepositoryTest extends TestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private TagReviewRepositoryImpl tagReviewRepository;

	@BeforeEach
	void setUp() {
		tagReviewRepository = new TagReviewRepositoryImpl(jdbcTemplate);
	}

	@Test
	void 태그에서_가장_많은_태그에_대한_정보를_3건_가져오는_쿼리가_제대로_동작하는가() {
		// given
		Long carOptionId = 110L;
		String result = "가격이 합리적이에요\uD83D\uDC4D";
		// when
		final List<TagResponseDTO> tagResponseDTOS = tagReviewRepository.findTagResponseDTOByCarOptionIdAndLimit(
			carOptionId, 5);

		// then
		softAssertions.assertThat(tagResponseDTOS).as("태그가 정말로 5건을 가져오는지").hasSize(5);
		final TagResponseDTO tagResponseDTO = tagResponseDTOS.get(0);
		softAssertions.assertThat(tagResponseDTO.getTagContent()).as("1등의 Content 확인").isEqualTo(result);
	}
}