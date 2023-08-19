package com.backend.topcariving.domain.archive.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.backend.topcariving.domain.archive.dto.CarDTO;
import com.backend.topcariving.domain.option.entity.CarOption;

public class CarDTORowMapper implements RowMapper<List<CarDTO>> {

	@Override
	public List<CarDTO> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<Long, CarDTO> result = new HashMap<>();

		do {
			long archivingId = rs.getLong("archiving_id");

			if (!result.containsKey(archivingId)) {
				CarDTO newCarDTO = CarDTO.builder()
					.archivingId(archivingId)
					.dayTime(rs.getTimestamp("day_time").toLocalDateTime())
					.isComplete(rs.getBoolean("is_complete"))
					.isAlive(rs.getBoolean("is_alive"))
					.archivingType(rs.getString("archiving_type"))
					.carOptions(new ArrayList<>()).build();
				result.put(archivingId, newCarDTO);
			}

			CarDTO carDTO = result.get(archivingId);
			List<CarOption> carOptions = carDTO.getCarOptions();

			CarOption newCarOptions = new CarOption(
				rs.getLong("car_option_id"),
				rs.getString("category"),
				rs.getString("category_detail"),
				rs.getString("option_name"),
				rs.getString("option_detail"),
				rs.getInt("price"),
				rs.getString("photo_url"),
				rs.getLong("parent_option_id")
			);
			carOptions.add(newCarOptions);
		} while (rs.next());

		return new ArrayList<>(result.values());
	}
}
