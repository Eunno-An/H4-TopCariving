package com.backend.topcariving.domain.archive.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.dto.request.FeedCopyRequestDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.SearchOptionDTO;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;
import com.backend.topcariving.domain.option.repository.CarOptionRepository;

@SpringBootTest
@Transactional
class ArchiveServiceIntegralTest extends TestSupport {

	@Autowired
	private CarOptionRepository carOptionRepository;

	@Autowired
	private MyCarRepository myCarRepository;

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

	@Nested
	@DisplayName("Feed 테스트")
	class Feed {

		@Test
		void 제대로_같은_피드에_있는_선택_값과_같은_값이_복사되어야_한다() {
			// given
			FeedCopyRequestDTO feedCopyRequestDTO = new FeedCopyRequestDTO(3L, 1L);

			// when
			Long archivingId = archiveService.saveFeedToCreatedCar(feedCopyRequestDTO);

			// then
			List<MyCar> copyMyCars = myCarRepository.findByArchivingId(archivingId);
			List<MyCar> originMyCars = myCarRepository.findByArchivingId(archivingId);

			softAssertions.assertThat(copyMyCars.size()).as("원본과 크기가 같은지 확인").isEqualTo(originMyCars.size());
			for (int i = 0; i < copyMyCars.size(); i++) {
				MyCar originMyCar = originMyCars.get(i);
				MyCar copyMyCar = copyMyCars.get(i);
				softAssertions.assertThat(originMyCar.getCarOptionId()).as("선택한 옵션이 같은지 확인").isEqualTo(copyMyCar.getCarOptionId());
			}
		}
	}

}