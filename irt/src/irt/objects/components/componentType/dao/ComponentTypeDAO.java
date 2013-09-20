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
	private List<ComponentType> componentTypeList;
	private final RowMapper<ComponentType> rowMapper = new RowMapper<ComponentType>() {
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

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ComponentType> getComponentTypeList(char groupId){

		if(componentTypeList==null || this.groupId!=groupId){
			this.groupId=groupId;
			String sql = "SELECT*FROM`second_and_third_digit`WHERE`id_first`='" +groupId+ "'";
			componentTypeList = jdbcTemplate.query(sql, rowMapper);
		}

		return componentTypeList;
	}

	public List<ComponentType> getComponentTypeList() {
		return componentTypeList;
	}
}
