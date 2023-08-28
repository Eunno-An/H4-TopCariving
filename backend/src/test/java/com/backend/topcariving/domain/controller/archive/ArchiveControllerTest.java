package com.backend.topcariving.domain.controller.archive;

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

import com.backend.topcariving.domain.dto.archive.response.ArchiveDetailResponseDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveFeedDTO;
import com.backend.topcariving.domain.dto.archive.response.ArchiveResponseDTO;
import com.backend.topcariving.domain.dto.archive.response.CreatedCarDTO;
import com.backend.topcariving.domain.dto.archive.response.SearchOptionDTO;
import com.backend.topcariving.domain.dto.bookmark.request.BookmarkRequestDTO;
import com.backend.topcariving.domain.entity.archive.CarArchiving;
import com.backend.topcariving.domain.entity.option.CarOption;
import com.backend.topcariving.domain.entity.option.enums.Category;
import com.backend.topcariving.domain.entity.option.enums.CategoryDetail;
import com.backend.topcariving.domain.service.archive.ArchiveService;
import com.backend.topcariving.domain.service.archive.BookmarkService;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ArchiveController.class)
class ArchiveControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ArchiveService archiveService;

	@MockBean
	private BookmarkService bookmarkService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	void 아카이빙_메인_전체_결과를_확인할_수_있어야_한다() throws Exception {
		// given
		List<Long> optionIds = List.of(103L, 110L);
		Integer pageNumber = 1;
		Integer pageSize = 1;

		CarOption carOption = CarOption.builder()
			.carOptionId(103L)
			.category(Category.CHOICE)
			.categoryDetail(CategoryDetail.SELECTED)
			.optionName("컴포트 II")
			.build();
		List<SearchOptionDTO> searchOptionDTOs = List.of(SearchOptionDTO.from(carOption));
		CarArchiving carArchiving = CarArchiving.builder()
			.archivingId(1L)
			.build();
		List<ArchiveFeedDTO> archiveFeedDTOs = List.of(
			ArchiveFeedDTO.of(carArchiving, null, null, null)
		);
		ArchiveResponseDTO archiveResponseDTO = ArchiveResponseDTO.of(searchOptionDTOs, archiveFeedDTOs);
		given(archiveService.archivingSearch(any(), any(), any()))
			.willReturn(archiveResponseDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/api/archiving/result")
			.param("optionIds", String.valueOf(optionIds.get(0)))
			.param("optionIds", String.valueOf(optionIds.get(1)))
			.param("pageNumber", String.valueOf(pageNumber))
			.param("pageSize", String.valueOf(pageSize)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.options").isArray())
			.andExpect(jsonPath("$.options[0].carOptionId").value(103L))
			.andExpect(jsonPath("$.archiveSearchResponses").isArray())
			.andExpect(jsonPath("$.archiveSearchResponses[0].archivingId").value(1L));
	}

	@Test
	void 마이카이빙_내가_만든_차량_목록을_조회할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Integer pageNumber = 1;
		Integer pageSize = 1;

		List<CreatedCarDTO> createdCarDTOs = List.of(
			CreatedCarDTO.builder()
				.archivingId(1L)
				.build()
		);
		given(archiveService.getCreatedCars(any(), any(), any()))
			.willReturn(createdCarDTOs);

		// when
		ResultActions resultActions = mvc.perform(get("/api/archiving/created-cars")
				.param("userId", String.valueOf(userId))
				.param("pageNumber", String.valueOf(pageNumber))
				.param("pageSize", String.valueOf(pageSize)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].archivingId").value(1L));
	}

	@Test
	void 마이카이빙_피드에서_저장한_차량_목록을_조회할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Integer pageNumber = 1;
		Integer pageSize = 1;

		List<ArchiveFeedDTO> archiveFeedDTOs = List.of(
			ArchiveFeedDTO.builder()
				.archivingId(1L)
				.build()
		);
		given(archiveService.getFeedCars(any(), any(), any()))
			.willReturn(archiveFeedDTOs);

		// when
		ResultActions resultActions = mvc.perform(get("/api/archiving/feeds")
			.param("userId", String.valueOf(userId))
			.param("pageNumber", String.valueOf(pageNumber))
			.param("pageSize", String.valueOf(pageSize)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].archivingId").value(1L));
	}

	@Test
	void 마이카이빙_피드에_있는_차량을_내가_만든_차량으로_복사할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 1L;

		given(archiveService.saveFeedToCreatedCar(any(), any()))
			.willReturn(21L);

		// when
		ResultActions resultActions = mvc.perform(post("/api/archiving/feeds/{archivingId}", archivingId)
				.param("userId", String.valueOf(userId))
				.param("archivingId", String.valueOf(archivingId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(21L));
	}

	@Test
	void 차량의_세부_정보를_조회할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 1L;

		ArchiveDetailResponseDTO archiveDetailResponseDTO = ArchiveDetailResponseDTO.builder()
					.archivingId(1L)
					.build();
		given(archiveService.getDetailsCars(any(), any()))
			.willReturn(archiveDetailResponseDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/api/archiving/details/{archivingId}", archivingId)
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.archivingId").value(1L));
	}

	@Test
	void 차량_북마크를_추가_및_삭제할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		BookmarkRequestDTO bookmarkRequestDTO = new BookmarkRequestDTO(2L, true);
		String content = objectMapper.writeValueAsString(bookmarkRequestDTO);

		given(bookmarkService.toggleBookmark(any(), any()))
			.willReturn(true);

		// when
		ResultActions resultActions = mvc.perform(post("/api/archiving/feeds/bookmarks")
			.param("userId", String.valueOf(userId))
			.contentType(MediaType.APPLICATION_JSON)
			.content(content));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(true));
	}

	@Test
	void 마이카이빙에_있는_내가_만든_차량을_삭제할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 1L;

		given(archiveService.deleteMyArchiving(any(), any()))
			.willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(delete("/api/archiving/created-cars/{archivingId}", archivingId)
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 마이카이빙에_있는_내가_만든_차량_삭제를_되돌릴_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 1L;

		given(archiveService.restoreMyArchiving(any(), any()))
			.willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/archiving/created-cars/{archivingId}", archivingId)
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 마이카이빙에_있는_피드에서_저장한_차량을_삭제할_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 2L;

		given(bookmarkService.deleteMyArchiving(any(), any()))
			.willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(delete("/api/archiving/feed-cars/{archivingId}", archivingId)
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

	@Test
	void 마이카이빙에_있는_피드에서_저장한_차량_삭제를_되돌릴_수_있어야_한다() throws Exception {
		// given
		Long userId = 1L;
		Long archivingId = 2L;

		given(bookmarkService.restoreMyArchiving(any(), any()))
			.willReturn(archivingId);

		// when
		ResultActions resultActions = mvc.perform(post("/api/archiving/feed-cars/{archivingId}", archivingId)
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(archivingId));
	}

}