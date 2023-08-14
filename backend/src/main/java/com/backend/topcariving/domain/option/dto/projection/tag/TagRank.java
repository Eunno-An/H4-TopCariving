package com.backend.topcariving.domain.option.dto.projection.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class TagRank {

	private Long carOptionId;

	private String tagText;

	private String optionName;

	private Integer reviewCount;

}
