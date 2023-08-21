package com.backend.topcariving.domain.entity.option;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Position {

	private Long positionId;

	private String leftPixel;

	private String topPixel;

	private String leftPercent;

	private String topPercent;

	// FK
	private Long carOptionId;

}
