package com.backend.topcariving.domain.controller.option;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.backend.topcariving.domain.dto.option.request.esitmation.EstimationChangeRequestDTO;
import com.backend.topcariving.domain.dto.option.response.estimation.OptionSummaryDTO;
import com.backend.topcariving.domain.dto.option.response.estimation.SummaryResponseDTO;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.service.option.EstimationService;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EstimationController.class)
class EstimationControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EstimationService estimationService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	void 견적_요약보기에서_변경사항을_저장할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 1L;
		List<Long> optionIds = List.of(103L, 110L);
		EstimationChangeRequestDTO estimationChangeRequestDTO = EstimationChangeRequestDTO.builder()
			.archivingId(archivingId)
			.optionIds(optionIds)
			.build();
		String content = objectMapper.writeValueAsString(estimationChangeRequestDTO);

		given(estimationService.changeOptions(any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/estimations/")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(content));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 내_차_요약_보기_및_전체_결과_확인을_수행할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 1L;
		List<OptionSummaryDTO> optionSummaryDTOs = List.of(
			new OptionSummaryDTO(1L, "Le Blanc", Category.TRIM, CategoryDetail.MODEL, 1000000, "photoUrl", null)
		);
		Map<String, List<OptionSummaryDTO>> map = new HashMap<>();
		map.put("르블랑", optionSummaryDTOs);
		SummaryResponseDTO summaryResponseDTO = new SummaryResponseDTO("photoUrl", map);

		given(estimationService.summary(any(), any())).willReturn(summaryResponseDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/estimations/summary/{archivingId}", archivingId)
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.photoUrl").value("photoUrl"))
			.andExpect(jsonPath("$.options").isMap())
			.andExpect(jsonPath("$.options.르블랑[0].carOptionId").value(1L));
	}

}