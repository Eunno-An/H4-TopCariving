package com.backend.topcariving.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthInfoDTO {

	private String id;

	private String email;

	private String name;

	private String mobileNum;

	private String birthdate;

	private String lang;

	private boolean social;
}
