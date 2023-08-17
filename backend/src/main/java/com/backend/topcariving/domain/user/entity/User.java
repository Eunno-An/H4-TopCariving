package com.backend.topcariving.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

	private Long userId;

	private String name;

	private String email;

	private String password;
}
