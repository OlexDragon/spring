package irt.objects.components.componentType.dao;

import irt.objects.components.componentType.ComponentType;
import irt.objects.components.dao.AListDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class ComponentTypeDAO extends AListDAO<ComponentType>{

	private char groupId;

	public List<ComponentType> getComponentTypeList(char groupId){

		if(list==null || this.groupId!=groupId){
			this.groupId=groupId;
			list = getList(true);
		}

		return list;
	}

	@Override
	protected RowMapper<ComponentType> getRowMapper() {
		return new RowMapper<ComponentType>() {
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
	}

	@Override
	protected String getQuery() {
		return "SELECT*FROM`irt`.`second_and_third_digit`WHERE`id_first`='" +groupId+ "'";
	}
}
