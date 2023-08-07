package com.backend.topcariving.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonPropertyOrder("data")
@AllArgsConstructor
public class SuccessResponse<T> {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
}
