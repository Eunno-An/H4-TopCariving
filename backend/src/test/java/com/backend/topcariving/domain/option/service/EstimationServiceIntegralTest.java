package com.backend.topcariving.domain.option.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.backend.topcariving.config.TestSupport;
import com.backend.topcariving.domain.option.dto.response.archiving.ArchivingResponseDTO;

@SpringBootTest
@Transactional
public class EstimationServiceIntegralTest extends TestSupport {

	@Autowired
	private EstimationService estimationService;

	@Nested
	@DisplayName("내 차 만들기 결과 조회")
	class MyCarCreation {
		@Test
		void 내_차_만들기_결과를_조회할_수_있어야_한다() {
			// given
			Long userId = 1L;
			Long archivingId = 1L;

			// when
			ArchivingResponseDTO archivingResponseDTO = estimationService.getArchivingResult(userId, archivingId);

			// then
			softAssertions.assertThat(archivingResponseDTO.getArchivingId()).as("아카이빙 아이디 테스트").isEqualTo(1L);
			softAssertions.assertThat(archivingResponseDTO.getModel().getCarOptionId()).as("모델 테스트").isEqualTo(1L);
			softAssertions.assertThat(archivingResponseDTO.getEngine().getCarOptionId()).as("엔진 테스트").isEqualTo(5L);
			softAssertions.assertThat(archivingResponseDTO.getBodyType().getCarOptionId()).as("바디타입 테스트").isEqualTo(7L);
			softAssertions.assertThat(archivingResponseDTO.getDrivingMethod().getCarOptionId()).as("구동방식 테스트").isEqualTo(9L);
			softAssertions.assertThat(archivingResponseDTO.getExteriorColor().getCarOptionId()).as("외장색상 테스트").isEqualTo(11L);
			softAssertions.assertThat(archivingResponseDTO.getInteriorColor().getCarOptionId()).as("내장색상 테스트").isEqualTo(17L);
		}
	}
}
