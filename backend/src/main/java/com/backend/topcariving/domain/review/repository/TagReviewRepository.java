package com.backend.topcariving.domain.review.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.topcariving.domain.option.dto.response.tag.TagResponseDTO;
import com.backend.topcariving.domain.review.entity.TagReview;

@Repository
public interface TagReviewRepository extends CrudRepository<TagReview, Long> {

	@Query("SELECT TAG.tag_text AS TAG_CONTENT, COUNT(*) \n"
		+ "FROM MY_CAR AS MC \n"
		+ "INNER JOIN TAG_REVIEW AS TR ON MC.my_car_id = TR.my_car_id \n"
		+ "INNER JOIN CAR_OPTION AS CO ON MC.car_option_id = CO.car_option_id \n"
		+ "INNER JOIN TAG ON TAG.tag_id = TR.tag_id \n"
		+ "WHERE CO.car_option_id = :carOptionId \n"
		+ "GROUP BY (TAG_TEXT, OPTION_NAME) \n"
		+ "ORDER BY (COUNT(*), TAG_TEXT) DESC \n"
		+ "LIMIT 3;")
	List<TagResponseDTO> findTagResponseDTOByCarOptionId(@Param("carOptionId")Long carOptionId);
}
