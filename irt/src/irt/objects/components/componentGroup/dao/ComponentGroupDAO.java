package irt.objects.components.componentGroup.dao;

import irt.objects.components.componentGroup.ComponentGroup;
import irt.objects.components.dao.ADAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class ComponentGroupDAO extends ADAO<ComponentGroup>{

	private RowMapper<ComponentGroup> rowMapper = new RowMapper<ComponentGroup>() {
			@Override
			public ComponentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
				ComponentGroup componentGroup = new ComponentGroup();
				componentGroup.setId(rs.getString("id").charAt(0));
				componentGroup.setDescription(rs.getString("description"));
				return componentGroup;
			}
		};

	public List<ComponentGroup> getComponentGroups(){
		return getList("SELECT*FROM`irt`.`first_digit`", rowMapper);
	}
}
