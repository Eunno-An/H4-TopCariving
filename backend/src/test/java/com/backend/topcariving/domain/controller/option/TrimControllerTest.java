package com.backend.topcariving.domain.controller.option;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.backend.topcariving.domain.dto.option.request.SelectOptionRequestDTO;
import com.backend.topcariving.domain.dto.option.response.engine.EngineResponseDTO;
import com.backend.topcariving.domain.dto.option.response.model.ModelPhotoDTO;
import com.backend.topcariving.domain.dto.option.response.model.ModelResponseDTO;
import com.backend.topcariving.domain.dto.option.response.trim.OptionResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.EngineDetail;
import com.backend.topcariving.domain.entity.option.ModelPhoto;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.service.option.TrimService;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TrimController.class)
class TrimControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TrimService trimService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	void 모델_옵션_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(1L)
			.category(Category.TRIM)
			.categoryDetail(CategoryDetail.MODEL)
			.optionName("Le Blanc")
			.build();
		ModelPhoto modelPhoto = new ModelPhoto(1L, "content", "photoSvgUrl", "photoPngUrl", 1L);
		ModelPhotoDTO modelPhotoDTO = ModelPhotoDTO.from(modelPhoto);
		ModelResponseDTO modelResponseDTO = ModelResponseDTO.of(carOption, List.of(modelPhotoDTO));

		given(trimService.getModels()).willReturn(List.of(modelResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/trims/models"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(1L))
			.andExpect(jsonPath("$[0].optionName").value("Le Blanc"))
			.andExpect(jsonPath("$[0].photos").isArray())
			.andExpect(jsonPath("$[0].photos[0].content").value("content"))
			.andExpect(jsonPath("$[0].photos[0].photoSvgUrl").value("photoSvgUrl"))
			.andExpect(jsonPath("$[0].photos[0].photoPngUrl").value("photoPngUrl"));
	}

	@Test
	void 모델_옵션을_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 1L;
		Long archivingId = 21L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, null);

		given(trimService.saveModel(any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/trims/models")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 엔진_옵션_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(5L)
			.category(Category.TRIM)
			.categoryDetail(CategoryDetail.ENGINE)
			.optionName("디젤 2.2")
			.build();
		EngineDetail engineDetail = new EngineDetail(1L, "maxOutput", "maxTorque", 5L);
		EngineResponseDTO engineResponseDTO = EngineResponseDTO.of(carOption, engineDetail);

		given(trimService.getEngines()).willReturn(List.of(engineResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/trims/engines"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(5L))
			.andExpect(jsonPath("$[0].optionName").value("디젤 2.2"))
			.andExpect(jsonPath("$[0].maxOutput").value("maxOutput"))
			.andExpect(jsonPath("$[0].maxTorque").value("maxTorque"));
	}

	@Test
	void 엔진_옵션을_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 5L;
		Long archivingId = 1L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, archivingId);

		given(trimService.saveOption(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/trims/engines")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 바디타입_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(7L)
			.category(Category.TRIM)
			.categoryDetail(CategoryDetail.BODY_TYPE)
			.optionName("7인승")
			.build();
		OptionResponseDTO optionResponseDTO = OptionResponseDTO.from(carOption);

		given(trimService.getOptions(CategoryDetail.BODY_TYPE)).willReturn(List.of(optionResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/trims/body-types"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(7L))
			.andExpect(jsonPath("$[0].optionName").value("7인승"));
	}

	@Test
	void 바디타입을_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 7L;
		Long archivingId = 1L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, archivingId);

		given(trimService.saveOption(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/trims/body-types")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 구동방식_전체를_반환해야한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(9L)
			.category(Category.TRIM)
			.categoryDetail(CategoryDetail.DRIVING_METHOD)
			.optionName("2WD")
			.build();
		OptionResponseDTO optionResponseDTO = OptionResponseDTO.from(carOption);

		given(trimService.getOptions(CategoryDetail.DRIVING_METHOD)).willReturn(List.of(optionResponseDTO));

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/trims/driving-methods"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(9L))
			.andExpect(jsonPath("$[0].optionName").value("2WD"));
	}

	@Test
	void 구동방식을_저장해야한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 9L;
		Long archivingId = 1L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, archivingId);

		given(trimService.saveOption(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/trims/driving-methods")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	private String jsonToString(Object value) throws JsonProcessingException {
		return objectMapper.writeValueAsString(value);
	}

}