package irt.objects.components.componentType.dao;

import irt.objects.components.componentType.ComponentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ComponentTypeDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ComponentType> getListComponentGroup(char groupId){
		String sql = "SELECT*FROM`second_and_third_digit`WHERE`id_first`='" +groupId+ "'";

		return jdbcTemplate.query(sql, new RowMapper<ComponentType>() {
			@Override
			public ComponentType mapRow(ResultSet rs, int rowNum) throws SQLException {

				ComponentType componentType = new ComponentType();

				componentType.setGroupId(rs.getString("id_first").charAt(0));
				componentType.setType(rs.getString("id"));
				componentType.setDescription(rs.getString("description"));
				componentType.setClassId(rs.getInt("class_id"));

				return componentType;
			}
		});
	}
}
