package com.backend.topcariving.domain.entity.user;

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
