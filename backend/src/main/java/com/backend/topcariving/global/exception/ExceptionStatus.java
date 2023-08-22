package com.backend.topcariving.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionStatus {

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
	INVALID_OAUTH(HttpStatus.UNAUTHORIZED, "Oauth 인증 오류입니다."),
	INVALID_AUTHORITY(HttpStatus.UNAUTHORIZED, "이 사용자는 권한이 없습니다."),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보가 없습니다."),
	ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 가입된 유저입니다."),

	INVALID_ARCHIVING_ID(HttpStatus.BAD_REQUEST, "차량 아카이빙 아이디가 유효하지 않습니다."),
	INVALID_MY_CAR_ID(HttpStatus.BAD_REQUEST, "내 차 아이디가 유효하지 않습니다."),
	INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "같은 카테고리 범주만 변경이 가능합니다"),
	INVALID_CAR_OPTION_ID(HttpStatus.BAD_REQUEST, "차량 옵션 아이디가 유효하지 않습니다.");

	private final HttpStatus code;
	private final String message;

}
