package irt.objects.components.componentGroup.dao;

import irt.objects.components.componentGroup.ComponentGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ComponentGroupDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ComponentGroup> getListComponentGroup(){
		String sql = "SELECT*FROM`first_digit`";

		return jdbcTemplate.query(sql, new RowMapper<ComponentGroup>() {
			@Override
			public ComponentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComponentGroup componentGroup = new ComponentGroup();
 				componentGroup.setId(rs.getString("id").charAt(0));
 				componentGroup.setDescription(rs.getString("description"));
				return componentGroup;
			}
		});
	}
}
