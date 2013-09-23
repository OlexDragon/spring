package irt.objects.components.componentType.dao;

import irt.objects.components.componentType.ComponentType;
import irt.objects.components.dao.ADAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class ComponentTypeDAO extends ADAO<ComponentType>{

	private RowMapper<ComponentType> rowMapper = new RowMapper<ComponentType>() {
		@Override
		public ComponentType mapRow(ResultSet rs, int rowNum)throws SQLException {

			ComponentType componentType = new ComponentType();

			componentType.setGroupId(rs.getString("id_first").charAt(0));
			componentType.setType(rs.getString("id"));
			componentType.setDescription(rs.getString("description"));
			componentType.setClassId(rs.getInt("class_id"));

			return componentType;
		}
	};
	private char groupId;

	public List<ComponentType> getComponentTypes(char groupId){
		this.groupId = groupId;
		return getList("SELECT*FROM`irt`.`second_and_third_digit`WHERE`id_first`='" +groupId+ "'", rowMapper);
	}

	public List<ComponentType> getComponentTypes() {
		return getComponentTypes(groupId);
	}
}
