package com.backend.topcariving.domain.archive.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.archive.dto.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.archive.dto.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.archive.dto.response.CreatedCarDTO;
import com.backend.topcariving.domain.archive.dto.response.SearchOptionDTO;
import com.backend.topcariving.domain.archive.entity.ArchivingType;
import com.backend.topcariving.domain.archive.entity.CarArchiving;
import com.backend.topcariving.domain.archive.entity.MyCar;
import com.backend.topcariving.domain.archive.repository.CarArchivingRepository;
import com.backend.topcariving.domain.archive.repository.MyCarRepository;

@SpringBootTest
@Transactional
class ArchiveServiceIntegralTest extends TestSupport {

	@Autowired
	private MyCarRepository myCarRepository;

	@Autowired
	private CarArchivingRepository carArchivingRepository;

	@Autowired
	private ArchiveService archiveService;

	@Nested
	@DisplayName("아카이빙 전체 화면 테스트")
	class BasicOption {
		@Test
		void 필터링을_위해_필요한_선택_품목을_반환해야한다() {
			// given
			List<Long> optionIds = List.of(103L, 110L);
			Integer pageNumber = 1;
			Integer pageSize = 3;

			// when
			ArchiveResponseDTO archiveResponseDTO = archiveService.archivingSearch(optionIds, pageNumber, pageSize);

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
			Integer pageNumber = 1;
			Integer pageSize = 3;

			// when
			ArchiveResponseDTO archiveResponseDTO = archiveService.archivingSearch(optionIds, pageNumber, pageSize);

			// then
			List<ArchiveFeedDTO> archiveFeedDTOs = archiveResponseDTO.getArchiveSearchResponses();
			softAssertions.assertThat(archiveFeedDTOs).hasSize(3);
			softAssertions.assertThat(archiveFeedDTOs.get(0).getArchivingId()).as("3이 반환되어야 함").isEqualTo(3L);
			softAssertions.assertThat(archiveFeedDTOs.get(1).getArchivingId()).as("2가 반환되어야 함").isEqualTo(2L);
			softAssertions.assertThat(archiveFeedDTOs.get(2).getArchivingId()).as("1이 반환되어야 함").isEqualTo(1L);
		}

		@Test
		void optionIds_값이_있을_때_필터링된_결과를_반환해야한다() {
			// given
			List<Long> optionIds = List.of(103L, 110L);
			Integer pageNumber = 1;
			Integer pageSize = 3;

			// when
			ArchiveResponseDTO archiveResponseDTO = archiveService.archivingSearch(optionIds, pageNumber, pageSize);

			// then
			List<ArchiveFeedDTO> archiveFeedDTOs = archiveResponseDTO.getArchiveSearchResponses();
			softAssertions.assertThat(archiveFeedDTOs).hasSize(2);
			softAssertions.assertThat(archiveFeedDTOs.get(0).getArchivingId()).as("3이 반환되어야 함").isEqualTo(3L);
			softAssertions.assertThat(archiveFeedDTOs.get(1).getArchivingId()).as("1이 반환되어야 함").isEqualTo(1L);
		}
	}

	@Test
	void 차량의_세부_정보를_반환해야한다() {
		// given
		Long userId = 3L;
		Long archivingId = 1L;

		// when
		ArchiveDetailResponseDTO detailsCars = archiveService.getDetailsCars(userId, archivingId);

		// then
		softAssertions.assertThat(detailsCars.getArchivingId())
			.as("차량 아카이빙 ID 검증").isEqualTo(1L);
		softAssertions.assertThat(detailsCars.getDayTime())
			.as("시승 및 구매 일시 검증").isEqualTo("2023-08-01T12:00:00");
		softAssertions.assertThat(detailsCars.getArchivingType())
			.as("차량 아카이빙 타입 검증").isEqualTo("시승");
		softAssertions.assertThat(detailsCars.getTotalPrice())
			.as("총 가격 검증").isEqualTo(45240000);
		softAssertions.assertThat(detailsCars.getPositions())
			.as("포지션 리스트 검증").hasSize(0);
		softAssertions.assertThat(detailsCars.getIsBookmarked())
			.as("북마크 여부 검증").isEqualTo(false);
		softAssertions.assertThat(detailsCars.getOptionDetails())
			.as("선택 옵션 디테일 검증").hasSize(8);
		softAssertions.assertThat(detailsCars.getCarReview())
			.as("차량 전체 텍스트 리뷰 검증").isEqualTo("너무 좋아요");
		softAssertions.assertThat(detailsCars.getTags())
			.as("차량 전체 태그 리뷰 검증").hasSize(3);
	}

	@Nested
	@DisplayName("Feed 테스트")
	class Feed {

		@Test
		void 제대로_같은_피드에_있는_선택_값과_같은_값이_복사되어야_한다() {
			// given
			Long userId = 3L;
			Long archivingId = 1L;

			// when
			Long copyArchivingId = archiveService.saveFeedToCreatedCar(userId, archivingId);

			// then
			List<MyCar> copyMyCars = myCarRepository.findByArchivingId(copyArchivingId);
			List<MyCar> originMyCars = myCarRepository.findByArchivingId(copyArchivingId);

			softAssertions.assertThat(copyMyCars.size()).as("원본과 크기가 같은지 확인").isEqualTo(originMyCars.size());
			for (int i = 0; i < copyMyCars.size(); i++) {
				MyCar originMyCar = originMyCars.get(i);
				MyCar copyMyCar = copyMyCars.get(i);
				softAssertions.assertThat(originMyCar.getCarOptionId()).as("선택한 옵션이 같은지 확인").isEqualTo(copyMyCar.getCarOptionId());
			}
		}

		@Test
		void 제대로_피드의_값을_가져오는지_확인() {
			Long userId = 1L;
			Integer pageNumber = 2;
			Integer pageSize = 1;

			// when
			List<ArchiveFeedDTO> feedCars = archiveService.getFeedCars(userId, pageNumber, pageSize);

			// then
			softAssertions.assertThat(feedCars).as("피드의 결과는 1건이다").hasSize(1);
			ArchiveFeedDTO archiveFeedDTO = feedCars.get(0);
			softAssertions.assertThat(archiveFeedDTO.getCarReview()).as("자동차 리뷰 테스트").isEqualTo("너무 좋아요");
			softAssertions.assertThat(archiveFeedDTO.getType()).as("자동차 타입").isEqualTo(ArchivingType.DRIVE.getType());
			softAssertions.assertThat(archiveFeedDTO.getCarArchiveResult().get("트림")).as("트림의 개수").hasSize(3);
			softAssertions.assertThat(archiveFeedDTO.getCarArchiveResult().get("선택품목")).as("선택품목의 갯수").hasSize(2);
		}
	}

	@Nested
	@DisplayName("마이카이빙 테스트")
	class MyCariving {
		@Test
		void 내가_만든_차량_조회가_제대로_동작_하는지_테스트() {
			// given
			Long userId = 1L;
			Integer pageNumber = 1;
			Integer pageSize = 1;

			// when
			List<CreatedCarDTO> createdCars = archiveService.getCreatedCars(userId, pageNumber, pageSize);

			// then
			softAssertions.assertThat(createdCars).as("가져온 값의 크기 테스트").hasSize(1);
			CreatedCarDTO createdCarDTO = createdCars.get(0);
			softAssertions.assertThat(createdCarDTO.getTrims()).as("생성한 차량의 트림의 갯수").hasSize(4);
			softAssertions.assertThat(createdCarDTO.getSelectedOptions()).as("옵션의 크기").hasSize(2);
		}
	}

	@Nested
	@DisplayName("삭제 테스트")
	class delete {
		@Test
		void 마이카이빙의_내가_만든_차량_하나를_삭제할_수_있어야_한다() {
			// given
			Long userId = 1L;
			Long archivingId = 1L;

			// when
			Long deletedArchivingId = archiveService.deleteMyArchiving(userId, archivingId);

			// then
			CarArchiving carArchiving = carArchivingRepository.findById(deletedArchivingId).get();
			Assertions.assertThat(carArchiving.getIsAlive()).isFalse();
		}

		@Test
		void 마이카이빙의_내가_만든_차량_중_삭제한_차량을_되돌릴_수_있어야_한다() {
			// given
			Long userId = 1L;
			Long archivingId = 1L;
			Long deletedArchivingId = archiveService.deleteMyArchiving(userId, archivingId);

			// when
			Long restoredArchivingId = archiveService.restoreMyArchiving(userId, deletedArchivingId);

			// then
			CarArchiving carArchiving = carArchivingRepository.findById(restoredArchivingId).get();
			Assertions.assertThat(carArchiving.getIsAlive()).isTrue();
		}

	}
}
