package com.backend.topcariving.domain.archive.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.SearchOptionDTO;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

@SpringBootTest
@Transactional
class ArchiveServiceIntegralTest extends TestSupport {

	@Autowired
	private CarOptionRepository carOptionRepository;

	@Autowired
	private ArchiveService archiveService;

	@Nested
	@DisplayName("아카이빙 전체 화면 테스트")
	class BasicOption {
		@Test
		void 필터링을_위해_필요한_선택_품목을_반환해야한다() {
			// given
			List<Long> optionIds = List.of(103L, 110L);

			// when
			ArchiveResponseDTO archiveResponseDTO = archiveService.archivingSearch(optionIds);

			// then
			List<SearchOptionDTO> searchOptionDTOs = archiveResponseDTO.getOptions();
			softAssertions.assertThat(searchOptionDTOs).hasSize(15);
			softAssertions.assertThat(searchOptionDTOs.get(0).getCarOptionId()).as("103이 반환되어야 함").isEqualTo(103L);
			softAssertions.assertThat(searchOptionDTOs.get(1).getCarOptionId()).as("110이 반환되어야 함").isEqualTo(110L);
		}

		@Test
		void optionIds_null_일_때_모든_결과를_반환해야한다() {
			// given
			List<Long> optionIds = null;

			// when
			ArchiveResponseDTO archiveResponseDTO = archiveService.archivingSearch(optionIds);

			// then
			List<ArchiveFeedDTO> archiveFeedDTOs = archiveResponseDTO.getArchiveSearchResponses();
			softAssertions.assertThat(archiveFeedDTOs).hasSize(3);
			softAssertions.assertThat(archiveFeedDTOs.get(0).getArchivingId()).as("1이 반환되어야 함").isEqualTo(1L);
			softAssertions.assertThat(archiveFeedDTOs.get(1).getArchivingId()).as("2가 반환되어야 함").isEqualTo(2L);
			softAssertions.assertThat(archiveFeedDTOs.get(2).getArchivingId()).as("3이 반환되어야 함").isEqualTo(3L);
		}

		@Test
		void optionIds_값이_있을_때_필터링된_결과를_반환해야한다() {
			// given
			List<Long> optionIds = List.of(103L, 110L);

			// when
			ArchiveResponseDTO archiveResponseDTO = archiveService.archivingSearch(optionIds);

			// then
			List<ArchiveFeedDTO> archiveFeedDTOs = archiveResponseDTO.getArchiveSearchResponses();
			softAssertions.assertThat(archiveFeedDTOs).hasSize(2);
			softAssertions.assertThat(archiveFeedDTOs.get(0).getArchivingId()).as("1이 반환되어야 함").isEqualTo(1L);
			softAssertions.assertThat(archiveFeedDTOs.get(1).getArchivingId()).as("3이 반환되어야 함").isEqualTo(3L);
		}
	}

}