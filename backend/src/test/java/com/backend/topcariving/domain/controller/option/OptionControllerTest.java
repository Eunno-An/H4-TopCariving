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

import com.backend.topcariving.domain.dto.option.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.dto.option.request.SelectOptionsRequestDTO;
import com.backend.topcariving.domain.dto.option.response.selection.SelectionDetailDTO;
import com.backend.topcariving.domain.dto.option.response.selection.SelectionResponseDTO;
import com.backend.topcariving.domain.dto.option.response.trim.BasicOptionResponseDTO;
import com.backend.topcariving.domain.dto.option.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.service.option.OptionService;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = OptionController.class)
class OptionControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private OptionService optionService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	void 기본_옵션_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(103L)
			.category(Category.CHOICE)
			.categoryDetail(CategoryDetail.SELECTED)
			.optionName("컴포트 II")
			.build();
		OptionResponseDTO optionResponseDTO = OptionResponseDTO.from(carOption);
		Map<String, List<OptionResponseDTO>> map = new HashMap<>();
		map.put("선택옵션", List.of(optionResponseDTO));
		BasicOptionResponseDTO basicOptionResponseDTO = BasicOptionResponseDTO.of(map);
		given(optionService.getBasics()).willReturn(basicOptionResponseDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/basics"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isMap())
			.andExpect(jsonPath("$.data.선택옵션").isArray())
			.andExpect(jsonPath("$.data.선택옵션[0].carOptionId").value(103L));
	}

	@Test
	void 상세_품목_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(103L)
			.category(Category.CHOICE)
			.categoryDetail(CategoryDetail.SELECTED)
			.optionName("컴포트 II")
			.build();
		OptionResponseDTO optionResponseDTO = OptionResponseDTO.from(carOption);

		given(optionService.getSelections(CategoryDetail.SELECTED)).willReturn(List.of(optionResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/selections"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(103L))
			.andExpect(jsonPath("$[0].optionName").value("컴포트 II"));
	}

	@Test
	void 상세_품목을_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		List<Long> ids = List.of(103L, 110L);
		Long archivingId = 1L;
		SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(ids, archivingId);

		given(optionService.saveSelectionOptions(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/selections")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionsRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void H_Genuine_Accessories_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(120L)
			.category(Category.CHOICE)
			.categoryDetail(CategoryDetail.H_GENUINE_ACCESSORIES)
			.optionName("듀얼 머플러 패키지")
			.build();
		OptionResponseDTO optionResponseDTO = OptionResponseDTO.from(carOption);

		given(optionService.getSelections(CategoryDetail.H_GENUINE_ACCESSORIES)).willReturn(List.of(optionResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/accessories"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(120L))
			.andExpect(jsonPath("$[0].optionName").value("듀얼 머플러 패키지"));
	}

	@Test
	void H_Genuine_Accessories_를_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		List<Long> ids = List.of(120L, 121L, 122L);
		Long archivingId = 1L;
		SelectOptionsRequestDTO selectOptionsRequestDTO = new SelectOptionsRequestDTO(ids, archivingId);

		given(optionService.saveSelectionOptions(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/accessories")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionsRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void N_Performance_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(128L)
			.category(Category.CHOICE)
			.categoryDetail(CategoryDetail.N_PERFORMANCE)
			.optionName("20인치 다크 스퍼터링 휠")
			.build();
		OptionResponseDTO optionResponseDTO = OptionResponseDTO.from(carOption);

		given(optionService.getSelections(CategoryDetail.N_PERFORMANCE)).willReturn(List.of(optionResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/performances"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(128L))
			.andExpect(jsonPath("$[0].optionName").value("20인치 다크 스퍼터링 휠"));
	}

	@Test
	void N_Performance_를_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 128L;
		Long archivingId = 1L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, archivingId);

		given(optionService.saveSelectionOption(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/performances")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 선택_옵션의_디테일을_반환해야한다() throws Exception {
		// given
		Long carOptionId = 103L;
		CarOption carOption = CarOption.builder()
			.carOptionId(103L)
			.category(Category.CHOICE)
			.categoryDetail(CategoryDetail.SELECTED)
			.optionName("컴포트 II")
			.build();
		CarOption childCarOption = CarOption.builder()
			.carOptionId(104L)
			.optionName("후석 승객 알림")
			.build();
		SelectionDetailDTO selectionDetailDTO = SelectionDetailDTO.from(childCarOption);
		SelectionResponseDTO selectionResponseDTO = SelectionResponseDTO.of(carOption, List.of(selectionDetailDTO), null);

		given(optionService.getSelectionDetails(carOptionId)).willReturn(selectionResponseDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/details/{carOptionId}", carOptionId));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.carOptionId").value(103L))
			.andExpect(jsonPath("$.optionName").value("컴포트 II"))
			.andExpect(jsonPath("$.details").isArray())
			.andExpect(jsonPath("$.details[0].carOptionId").value(104L))
			.andExpect(jsonPath("$.details[0].optionName").value("후석 승객 알림"));
	}

	private String jsonToString(Object value) throws JsonProcessingException {
		return objectMapper.writeValueAsString(value);
	}

}