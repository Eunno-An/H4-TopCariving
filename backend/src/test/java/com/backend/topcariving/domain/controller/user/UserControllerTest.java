package com.backend.topcariving.domain.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.backend.topcariving.domain.service.user.UserService;
import com.backend.topcariving.global.auth.dto.LoginRequestDTO;
import com.backend.topcariving.global.auth.dto.TokenDTO;
import com.backend.topcariving.global.auth.service.OAuthService;
import com.backend.topcariving.global.auth.service.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private OAuthService oAuthService;

	@MockBean
	private TokenProvider tokenProvider;

	@Test
	void 로그인을_수행해야한다() throws Exception {
		// given
		String email = "topcariving@mail.com";
		String password = "1234";
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO(email, password);
		String content = objectMapper.writeValueAsString(loginRequestDTO);

		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);

		given(userService.login(any())).willReturn(tokenDTO);

		// when
		ResultActions resultActions = mvc.perform(post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken").value("accessToken"))
			.andExpect(jsonPath("$.refreshToken").value("refreshToken"));
	}

	@Test
	void reissue_를_수행해야한다() throws Exception {
		// given
		String bearerToken = "Bearer bearerToken";

		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);

		given(userService.reissueTokens(bearerToken)).willReturn(tokenDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/reissue")
			.header("Authorization", bearerToken));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken").value("accessToken"))
			.andExpect(jsonPath("$.refreshToken").value("refreshToken"));
	}

	@Test
	void 현대_OAuth_로_인증을_수행해야한다() throws Exception {
		// given
		String code = "code";
		String state = "state";

		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);

		given(oAuthService.authorize(state, code)).willReturn(tokenDTO);

		// when
		ResultActions resultActions = mvc.perform(get("/oauth/authorize")
			.param("code", code)
			.param("state", state));

		// then
		resultActions.andExpect(status().isFound())
			.andExpect(jsonPath("$.accessToken").value("accessToken"))
			.andExpect(jsonPath("$.refreshToken").value("refreshToken"));
	}

	@Test
	void 로그아웃을_수행해야한다() throws Exception {
		// given
		Long userId = 1L;

		// when
		ResultActions resultActions = mvc.perform(get("/logout")
			.param("userId", String.valueOf(userId)));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(header().doesNotExist("Authorization"));
	}

}