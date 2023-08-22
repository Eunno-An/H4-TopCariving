package com.backend.topcariving.domain.entity.user;

import com.backend.topcariving.global.auth.entity.enums.LoginType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

	private Long userId;

	private String name;

	private String email;

	private String password;

	private LoginType loginType;
}
