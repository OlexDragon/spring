package irt.objects.components.componentGroup.dao;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.dao.AListDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ComponentGroupDAO extends AListDAO<ComponentGroup>{

	@Override
	protected RowMapper<ComponentGroup> getRowMapper() {
		return new RowMapper<ComponentGroup>() {
			@Override
			public ComponentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComponentGroup componentGroup = new ComponentGroup();
				componentGroup.setId(rs.getString("id").charAt(0));
				componentGroup.setDescription(rs.getString("description"));
				return componentGroup;
			}
		};
	}

	@Override
	protected String getQuery() {
		return "SELECT*FROM`irt`.`first_digit`";
	}
}
