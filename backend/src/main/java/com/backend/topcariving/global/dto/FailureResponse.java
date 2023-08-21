package com.backend.topcariving.global.dto;

import org.springframework.http.HttpStatus;

import com.backend.topcariving.global.exception.ExceptionStatus;

import lombok.Getter;

@Getter
public class FailureResponse {

	private final HttpStatus code;
	private final String message;

	public FailureResponse(ExceptionStatus errorStatus) {
		this.code = errorStatus.getCode();
		this.message = errorStatus.getMessage();
	}
}
