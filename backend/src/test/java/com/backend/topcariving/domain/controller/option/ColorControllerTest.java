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
import com.backend.topcariving.domain.dto.option.request.color.BothColorRequestDTO;
import com.backend.topcariving.domain.dto.option.response.color.BothColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.ExteriorColorResponseDTO;
import com.backend.topcariving.domain.dto.option.response.color.InteriorColorResponseDTO;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.service.option.ColorService;
import com.backend.topcariving.domain.service.option.TrimService;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ColorController.class)
class ColorControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ColorService colorService;

	@MockBean
	private TrimService trimService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	void 외장_색상_옵션_전체를_반환할_수_있어야_한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(11L)
			.categoryDetail(CategoryDetail.EXTERIOR_COLOR)
			.optionName("어비스블랙펄")
			.build();
		List<ExteriorColorResponseDTO> exteriorColorResponseDTOs = List.of(ExteriorColorResponseDTO.of(carOption, null));
		given(colorService.getExteriorColors()).willReturn(exteriorColorResponseDTOs);

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/colors/exteriors"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(11L))
			.andExpect(jsonPath("$[0].optionName").value("어비스블랙펄"));
	}

	@Test
	void 외장_색상_옵션을_저장할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 11L;
		Long archivingId = 1L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, archivingId);

		given(trimService.saveOption(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/colors/exteriors")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 내장_색상_옵션_전체를_반환할_수_있어야_한다() throws Exception {
		// given
		CarOption carOption = CarOption.builder()
			.carOptionId(17L)
			.categoryDetail(CategoryDetail.INTERIOR_COLOR)
			.optionName("퀄팅천연(블랙)")
			.build();
		List<InteriorColorResponseDTO> interiorColorResponseDTOs = List.of(InteriorColorResponseDTO.of(carOption, null, null));
		given(colorService.getInteriorColors()).willReturn(interiorColorResponseDTOs);

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/colors/interiors"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].carOptionId").value(17L))
			.andExpect(jsonPath("$[0].optionName").value("퀄팅천연(블랙)"));
	}

	@Test
	void 내장_색상_옵션을_저장할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long carOptionId = 17L;
		Long archivingId = 1L;
		SelectOptionRequestDTO selectOptionRequestDTO = new SelectOptionRequestDTO(carOptionId, archivingId);

		given(trimService.saveOption(any(), any(), any())).willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/colors/interiors")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(selectOptionRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 외장_내장_색상_옵션_전체를_반환할_수_있어야_한다() throws Exception {
		// given
		CarOption exteriorColorOption = CarOption.builder()
			.carOptionId(11L)
			.categoryDetail(CategoryDetail.EXTERIOR_COLOR)
			.optionName("어비스블랙펄")
			.build();
		List<ExteriorColorResponseDTO> exteriorColorResponseDTOs = List.of(ExteriorColorResponseDTO.of(exteriorColorOption, null));
		CarOption interiorColorOption = CarOption.builder()
			.carOptionId(17L)
			.categoryDetail(CategoryDetail.INTERIOR_COLOR)
			.optionName("퀄팅천연(블랙)")
			.build();
		List<InteriorColorResponseDTO> interiorColorResponseDTOs = List.of(InteriorColorResponseDTO.of(interiorColorOption, null, null));

		BothColorResponseDTO bothColorResponseDTO = new BothColorResponseDTO(exteriorColorResponseDTOs, interiorColorResponseDTOs);
		given(colorService.getBothResponseDTO()).willReturn(bothColorResponseDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/api/options/colors/both"));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.exteriorColorResponses").isArray())
			.andExpect(jsonPath("$.exteriorColorResponses[0].carOptionId").value(11L))
			.andExpect(jsonPath("$.exteriorColorResponses[0].optionName").value("어비스블랙펄"))
			.andExpect(jsonPath("$.interiorColorResponses").isArray())
			.andExpect(jsonPath("$.interiorColorResponses[0].carOptionId").value(17L))
			.andExpect(jsonPath("$.interiorColorResponses[0].optionName").value("퀄팅천연(블랙)"));
	}

	@Test
	void 외장_내장_색상_옵션을_저장할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long exteriorColorOptionId = 11L;
		Long interiorColorOptionId = 17L;
		Long archivingId = 1L;
		BothColorRequestDTO bothColorRequestDTO = new BothColorRequestDTO(exteriorColorOptionId, interiorColorOptionId, archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/options/colors/both")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonToString(bothColorRequestDTO)));

		// then
		resultActions.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	private String jsonToString(Object value) throws JsonProcessingException {
		return objectMapper.writeValueAsString(value);
	}

}